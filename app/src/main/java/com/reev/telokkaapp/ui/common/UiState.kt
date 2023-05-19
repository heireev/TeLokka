package com.reev.telokkaapp.ui.common

import com.reev.telokkaapp.model.Place

sealed class UiState<out T: Any?> {
    object Loading : UiState<Nothing>()

    data class Success<out T: Any>(val data: Place?) : UiState<T>()

    data class Error(val errorMessage: String) : UiState<Nothing>()
}
