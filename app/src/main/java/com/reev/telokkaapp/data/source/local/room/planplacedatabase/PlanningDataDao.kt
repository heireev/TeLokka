package com.reev.telokkaapp.data.source.local.room.planplacedatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface PlanningDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanningData(data: PlanningData)
}