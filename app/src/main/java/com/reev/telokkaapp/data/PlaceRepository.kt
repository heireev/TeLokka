package com.reev.telokkaapp.data

import com.reev.telokkaapp.model.Place
import com.reev.telokkaapp.model.PlacesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlaceRepository {
    private val places = mutableListOf<Place>()
    fun getPlaces(): List<Place> {
        return PlacesData.dummyPlaces
    }

    fun getAllPlaces(): Flow<List<Place>> {
        return flowOf(places)
    }

    fun searchPlaces(query: String): List<Place>{
        return PlacesData.dummyPlaces.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getPlacesById(placeId: String): Place{
        return places.first {
            it.id == placeId
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