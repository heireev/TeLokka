package com.reev.telokkaapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.model.Place
import com.reev.telokkaapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PlaceRepository): ViewModel(){
    private val _uiState: MutableStateFlow<UiState<List<Place>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Place>>>
        get() = _uiState

    fun getAllPlaces() {
        viewModelScope.launch {
            repository.getAllPlaces()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{places ->
                    _uiState.value = UiState.Success(places)
                }
        }
    }

    private val _groupedPlaces = MutableStateFlow(
        repository.getPlaces()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedPlaces: StateFlow<Map<Char, List<Place>>> get() = _groupedPlaces

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedPlaces.value = repository.searchPlaces(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}