package com.reev.telokkaapp.data

import com.reev.telokkaapp.model.Place
import com.reev.telokkaapp.model.PlacesData

class PlaceRepository {
    fun getPlaces():  List<Place> {
        return PlacesData.places
    }

    fun searchPlaces(query: String): List<Place>{
        return PlacesData.places.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: PlaceRepository? = null

        fun getInstance(): PlaceRepository =
            instance ?: synchronized(this) {
                PlaceRepository().apply {
                    instance = this
                }
            }
    }
}