package com.example.krendikova.utils

import kotlin.coroutines.cancellation.CancellationException

inline fun <R> runCatchingNonCancellation(block: () -> R): kotlin.Result<R> {
    return try {
        kotlin.Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        e.printStackTrace()
        kotlin.Result.failure(e)
    }
}