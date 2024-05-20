package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FuellingEvent::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FuellingEventDatabase: RoomDatabase() {
    abstract fun fuellingEventDAO(): FuellingEventDAO
    companion object {
        @Volatile
        private var INSTANCE: FuellingEventDatabase? = null
        fun getDatabase(context: Context): FuellingEventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FuellingEventDatabase::class.java,
                    "fuelling_event_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}