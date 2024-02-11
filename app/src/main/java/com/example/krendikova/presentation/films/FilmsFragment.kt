package com.example.krendikova.presentation.films

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.krendikova.R
import com.example.krendikova.databinding.FragmentFilmsBinding
import com.example.krendikova.presentation.film_details.FilmDetailsFragment
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilmsFragment : Fragment() {

    private val type: Type by lazy {
        Type.valueOf(requireArguments().getString(ARG_TYPE) ?: Type.POPULAR.name)
    }
    private val viewModel: FilmsViewModel by viewModel { parametersOf(type) }

    private var _binding: FragmentFilmsBinding? = null
    private val binding: FragmentFilmsBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmsBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)

        toggleToolbars()
        handleSearch()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmsAdapter = FilmsAdapter(
            onClick = ::launchFragmentFilmDetails,
            onLongClick = viewModel::onFavoriteClick,
        )
        binding.filmsListLayout.rvFilms.adapter = filmsAdapter

        setTypeSettings()

        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.filmsListLayout.progress.isVisible = it.isLoading
                filmsAdapter.submitList(it.films)

                binding.filmsListLayoutContainer.isVisible = !it.isError
                binding.noneLayoutContainer.isVisible = it.isPlaceholder
                binding.errorLayoutContainer.isVisible = it.isError
            }
        }
        setClickListeners()
    }

    private fun setTypeSettings() {
        val colorActiveBtn =
            MaterialColors.getColor(requireContext(), R.attr.colorPrimaryVariant, Color.BLACK)
        val colorInactiveBtn =
            MaterialColors.getColor(requireContext(), R.attr.colorPrimaryContainer, Color.WHITE)
        val colorActiveBtnText =
            MaterialColors.getColor(requireContext(), R.attr.colorOnPrimary, Color.WHITE)
        val colorInactiveBtnText =
            MaterialColors.getColor(requireContext(), R.attr.colorOnPrimaryContainer, Color.BLACK)

        fun Button.bindColors(isActive: Boolean) {
            setBackgroundColor(if (isActive) colorActiveBtn else colorInactiveBtn)
            setTextColor(if (isActive) colorActiveBtnText else colorInactiveBtnText)
        }

        with(binding) {
            filmsListLayout.btnPopular.bindColors(isActive = type == Type.POPULAR)
            filmsListLayout.btnFavorite.bindColors(isActive = type == Type.FAVOURITE)
            toolbar1.title.text = when (type) {
                Type.FAVOURITE -> requireContext().getString(R.string.favorite_films)
                Type.POPULAR -> requireContext().getString(R.string.popular_films)
            }
        }
    }

    private fun setClickListeners() {
        binding.noneLayout.btnNone.setOnClickListener {
            closeEditToolbar()
            viewModel.loadFilms()
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        binding.errorLayout.btnRepeat.setOnClickListener {
            viewModel.loadFilms()
        }
        binding.filmsListLayout.btnFavorite.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, newInstance(Type.FAVOURITE))
                .commit()
        }
        binding.filmsListLayout.btnPopular.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, newInstance(Type.POPULAR))
                .commit()
        }
    }

    private fun launchFragmentFilmDetails(id: String): Unit {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FilmDetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    private fun toggleToolbars() {
        val transition = ChangeBounds()
        transition.duration = 1000

        binding.toolbar1.searchImg.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.toolbar2Container, transition)
            binding.toolbar1Container.isVisible = false
            binding.toolbar2Container.isVisible = true
        }
        binding.toolbar2.toolbarImg.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.toolbar2Container, transition)
            closeEditToolbar()
            viewModel.loadFilms()
        }
    }

    private fun handleSearch() {
        binding.toolbar2.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchQuery = s?.toString() ?: ""
                viewModel.loadFilms(searchQuery)
            }
        })
    }

    private fun closeEditToolbar() {
        binding.toolbar1Container.isVisible = true
        binding.toolbar2Container.isVisible = false
        binding.toolbar2.input.text = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        enum class Type { FAVOURITE, POPULAR }

        private const val ARG_TYPE = "arg_type"

        fun newInstance(type: Type): Fragment {
            return FilmsFragment().apply {
                arguments = bundleOf(
                    ARG_TYPE to type.name
                )
            }
        }
    }
}