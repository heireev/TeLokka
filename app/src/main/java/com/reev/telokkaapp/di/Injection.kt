package com.reev.telokkaapp.di

import com.reev.telokkaapp.data.PlaceRepository

object Injection {
    fun provideRepository(): PlaceRepository {
        return PlaceRepository.getInstance()
    }
}