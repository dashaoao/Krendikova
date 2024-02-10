package com.example.krendikova.presentation.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.krendikova.databinding.FragmentFilmsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsFragment : Fragment() {
    private val viewModel: FilmsViewModel by viewModel()
    private val filmsAdapter = FilmsAdapter()

    private var _binding: FragmentFilmsBinding? = null
    private val binding: FragmentFilmsBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmsBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}