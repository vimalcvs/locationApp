package com.example.locationapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class PermissionChecker (val context : Context){

    private val _fusedlocationclient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun  toallocateLresultfromFLPCtoCallbackandthenviewmodel(viewModel: LocationViewModel){
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let {
                val location = LocationData(Lattitude = it.latitude , Longitude = it.longitude)
                viewModel.update_locationValue(location)
            }
        }

    }}

    fun Questionlocationpermission(context : Context) : Boolean {

        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED



        }
    }
