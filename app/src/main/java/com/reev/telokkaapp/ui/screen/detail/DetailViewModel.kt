package com.reev.telokkaapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.model.Place
import com.reev.telokkaapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PlaceRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Place>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Place>>
        get() = _uiState

    fun getPlaceById(placeId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getPlacesById(placeId))
        }
    }


}