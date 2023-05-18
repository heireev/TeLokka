package com.reev.telokkaapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TeLokkaViewModel(private val repository: PlaceRepository): ViewModel(){
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

class ViewModelFactory(private val repository: PlaceRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeLokkaViewModel::class.java)) {
            return TeLokkaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}