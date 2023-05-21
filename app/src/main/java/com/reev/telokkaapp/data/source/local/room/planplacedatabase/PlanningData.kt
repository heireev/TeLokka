package com.reev.telokkaapp.data.source.local.room.planplacedatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "planning_data")
data class PlanningData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val title: String,
    val desc: String,
    val placeId: String,
    val placeName: String,
    val placeCategory: String,
    val placePhotoUrl: String,
    val placeMapUrl: String,
)
