package com.example.krendikova.presentation.film_details

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.krendikova.databinding.FragmentFilmDetailsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilmDetailsFragment : Fragment() {

    private val filmDetailsId: String = requireArguments().getString(FILM_ID)
        ?: throw IllegalArgumentException("FilmId was not found")

    private val viewModel: FilmDetailsViewModel by viewModel { parametersOf(filmDetailsId) }

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding: FragmentFilmDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmDetailsBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.filmsDetailsLayout.progress.isVisible = it.isLoading
                if (it.isError) {
                    showError()
                } else {
                    showScreen(it)
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.errorLayout.btnRepeat.setOnClickListener {
            viewModel.loadFilm()
        }
        binding.filmsDetailsLayout.toolbar.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun showError() {
        binding.filmsDetailsLayoutContainer.isVisible = false
        binding.errorLayoutContainer.isVisible = true
    }

    private fun showScreen(filmDetailsUiState: FilmDetailsUiState) {
        binding.filmsDetailsLayoutContainer.isVisible = true
        binding.errorLayoutContainer.isVisible = false
        binding.filmsDetailsLayout.bannerMaxImg.load(filmDetailsUiState.imgUrl)
        binding.filmsDetailsLayout.tvName.text = filmDetailsUiState.title
        binding.filmsDetailsLayout.tvDesc.text = filmDetailsUiState.descr
        val countriesText = SpannableStringBuilder()
            .bold { append("Страны: ") }
            .append(filmDetailsUiState.country)
        binding.filmsDetailsLayout.tvCountries.text = countriesText

        val genresText = SpannableStringBuilder()
            .bold { append("Жанры: ") }
            .append(filmDetailsUiState.genre)
        binding.filmsDetailsLayout.tvGenre.text = genresText
    }

    companion object {
        private const val FILM_ID = "filmId"

        fun newInstance(filmId: String): FilmDetailsFragment {
            return FilmDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(FILM_ID, filmId)
                }
            }
        }
    }
}