package com.reev.telokkaapp.data.source.local.room.planplacedatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanningData::class], version = 1)
abstract class PlanningDatabase : RoomDatabase() {
    abstract fun planningDataDao(): PlanningDataDao
}