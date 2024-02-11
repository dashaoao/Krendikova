package com.example.krendikova.presentation.films

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.krendikova.R
import kotlinx.coroutines.launch
import com.example.krendikova.databinding.FragmentFilmsBinding
import com.example.krendikova.presentation.film_details.FilmDetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsFragment : Fragment() {
    private val viewModel: FilmsViewModel by viewModel()
    private val filmsAdapter = FilmsAdapter(
        onClick = ::launchFragmentFilmDetails,
        onLongClick = {},
    )

    private var _binding: FragmentFilmsBinding? = null
    private val binding: FragmentFilmsBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmsBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)

//        toggleToolbars()
//        handleSearch()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filmsListLayout.rvFilms.adapter = filmsAdapter
        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.filmsListLayout.progress.isVisible = it.isLoading
                filmsAdapter.submitList(it.films)
                if (it.isError){
                    binding.filmsListLayoutContainer.isVisible  = false
                    binding.errorLayoutContainer.isVisible = true
                } else {
                    binding.filmsListLayoutContainer.isVisible  = true
                    binding.errorLayoutContainer.isVisible = false
                }
            }
        }
        binding.errorLayout.btnRepeat.setOnClickListener{
            viewModel.loadFilms()
        }
    }

    private fun launchFragmentFilmDetails(id: String) : Unit {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FilmDetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    private fun toggleToolbars() {
        val transition = ChangeBounds()
        transition.duration = 1000

        binding.toolbar1.searchImg.setOnClickListener{
            TransitionManager.beginDelayedTransition(binding.toolbar2Container, transition)
            binding.toolbar1Container.isVisible = false
            binding.toolbar2Container.isVisible = true
        }
        binding.toolbar2.toolbarImg.setOnClickListener{
            TransitionManager.beginDelayedTransition(binding.toolbar2Container, transition)
            closeEditToolbar()
            viewModel.loadFilms()
        }
    }

//    private fun handleSearch() {
//        binding.toolbar2.input.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                val searchQuery = s?.toString() ?: ""
//                if (searchQuery.isNotEmpty())
//                    viewModel.searchMoviesByWord(searchQuery)
//            }
//        })
//
//        binding.layoutNone.btnNone.setOnClickListener {
//            closeEditToolbar()
//            viewModel.loadFilms()
//        }
//    }

    private fun closeEditToolbar() {
        binding.toolbar1Container.isVisible = true
        binding.toolbar2Container.isVisible = false
        binding.toolbar2.input.text = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}