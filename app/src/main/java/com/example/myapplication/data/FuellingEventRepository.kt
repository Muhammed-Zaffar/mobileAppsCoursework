package com.example.myapplication.data

import androidx.lifecycle.LiveData

class FuellingEventRepository(private val FuellingEventDAO: FuellingEventDAO) {
    val allFuellingEvents: LiveData<List<FuellingEvent>> = FuellingEventDAO.getAllFuellingEvents()
    suspend fun insert(fuellingEvent: FuellingEvent) {
        FuellingEventDAO.insert(fuellingEvent)
    }
    suspend fun update(fuellingEvent: FuellingEvent) {
        FuellingEventDAO.update(fuellingEvent)
    }
    suspend fun delete(fuellingEvent: FuellingEvent) {
        FuellingEventDAO.delete(fuellingEvent)
    }
    fun getFuellingEvent(id: Int): LiveData<FuellingEvent> {
        return FuellingEventDAO.getFuellingEvent(id)
    }
}