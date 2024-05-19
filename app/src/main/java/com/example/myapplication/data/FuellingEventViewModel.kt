package com.example.myapplication.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState

class FuellingEventViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FuellingEventRepository
    val allFuellingEvents: LiveData<List<FuellingEvent>>

    init {
        val fuellingEventDAO = FuellingEventDatabase.getDatabase(application).fuellingEventDAO()
        repository = FuellingEventRepository(fuellingEventDAO)
        allFuellingEvents = repository.allFuellingEvents
        Log.d("FuellingEventViewModel", "init completed")
        Log.d("FuellingEventViewModel", "allFuellingEvents: ${allFuellingEvents.value}")
        Log.d("FuellingEventViewModel", "allFuellingEvents: ${allFuellingEvents.value?.size}")
        Log.d("FuellingEventViewModel", "allFuellingEvents: ${allFuellingEvents.isInitialized}")
    }

    fun insert(fuellingEvent: FuellingEvent) = viewModelScope.launch {
        repository.insert(fuellingEvent)
        Log.d("FuellingEventViewModel", "insert completed")
    }

    fun update(fuellingEvent: FuellingEvent) = viewModelScope.launch {
        repository.update(fuellingEvent)
    }

    fun delete(fuellingEvent: FuellingEvent) = viewModelScope.launch {
        repository.delete(fuellingEvent)
    }

    fun getFuellingEvent(id: Int): LiveData<FuellingEvent> {
        return repository.getFuellingEvent(id)
    }

}