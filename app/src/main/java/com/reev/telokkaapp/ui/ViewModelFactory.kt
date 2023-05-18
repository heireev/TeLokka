package com.reev.telokkaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.ui.screen.detail.DetailViewModel
import com.reev.telokkaapp.ui.screen.home.HomeViewModel
import com.reev.telokkaapp.ui.screen.planning.PlanningViewModel


class ViewModelFactory(private val repository: PlaceRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(PlanningViewModel::class.java)){
            return PlanningViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}