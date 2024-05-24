package com.example.myapplication.data

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
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
    fun getFuellingEventByID(id: Int): LiveData<FuellingEvent>


    @Query("SELECT * FROM fuelling_events ORDER BY date DESC")
    fun getAllFuellingEventsCursor(): Cursor
    @Query("SELECT * FROM fuelling_events WHERE id = :id")
    fun getFuellingEventByIDCursor(id: Int): Cursor
}