package com.example.locationapp

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class LocationViewmodel : ViewModel()  {

    private val _location = mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    fun update_locationValue(updatedlocation : LocationData ){
        _location.value = updatedlocation
    }
}