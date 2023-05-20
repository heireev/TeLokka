package com.reev.telokkaapp.data

import com.reev.telokkaapp.model.Place
import com.reev.telokkaapp.model.PlacesData

class PlaceRepository {
    fun getPlaces(): List<Place> {
        return PlacesData.dummyPlaces
    }

    fun searchPlaces(query: String): List<Place> {
        return PlacesData.dummyPlaces.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getPlacesById(placeId: String): Place? {
        return PlacesData.dummyPlaces.find { it.id == placeId }
    }


    companion object {
        @Volatile
        private var instance: PlaceRepository? = null

        fun getInstance(): PlaceRepository = instance ?: synchronized(this) {
            PlaceRepository().apply {
                instance = this
            }
        }
    }
}