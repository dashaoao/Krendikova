package com.example.krendikova.domain.usecase

import com.example.krendikova.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface GetFilmsUseCase {
    suspend operator fun invoke(keyword: String): Flow<List<Film>>
}