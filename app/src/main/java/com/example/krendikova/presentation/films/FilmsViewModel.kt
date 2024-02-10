package com.example.krendikova.presentation.films

import androidx.lifecycle.ViewModel
import com.example.krendikova.domain.repository.FilmsRepository

class FilmsViewModel(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

}