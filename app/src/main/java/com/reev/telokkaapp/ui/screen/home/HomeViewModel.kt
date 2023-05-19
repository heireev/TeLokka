package com.reev.telokkaapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: PlaceRepository): ViewModel(){

    //buat grouping
    private val _groupedPlaces = MutableStateFlow(
        repository.getPlaces()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedPlaces: StateFlow<Map<Char, List<Place>>> get() = _groupedPlaces


    //buat searching
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedPlaces.value = repository.searchPlaces(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}