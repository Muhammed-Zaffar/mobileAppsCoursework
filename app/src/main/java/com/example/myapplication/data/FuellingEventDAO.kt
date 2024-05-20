package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@androidx.room.Dao
interface FuellingEventDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fuellingEvent: FuellingEvent)

    @Update
    suspend fun update(fuellingEvent: FuellingEvent)

    @Delete
    suspend fun delete(fuellingEvent: FuellingEvent)

    @Query("SELECT * FROM fuelling_events ORDER BY date DESC")
    fun getAllFuellingEvents(): LiveData<List<FuellingEvent>>

    @Query("SELECT * FROM fuelling_events WHERE id = :id")
    fun getFuellingEvent(id: Int): LiveData<FuellingEvent>

}