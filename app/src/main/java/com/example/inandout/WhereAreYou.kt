package com.example.inandout

import android.app.Activity
import android.location.Address
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat.getSystemService
import org.json.JSONObject
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.*
fun getCurrentAddress2(context: Context): String {
    var whereAreYou = ""
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
    ) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationProvider = LocationManager.NETWORK_PROVIDER

        try {
            val lastKnownLocation = locationManager.getLastKnownLocation(locationProvider)

            if (lastKnownLocation != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                val latitude = lastKnownLocation.latitude
                val longitude = lastKnownLocation.longitude
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0]
                        whereAreYou = "${address.adminArea} ${address.subAdminArea}"
                    } else {
                        whereAreYou = "위치를 찾을 수 없습니다."
                    }
                }
            } else {
                whereAreYou = "위치 정보를 가져올 수 없습니다."
            }
        } catch (e: SecurityException) {
            // 위치 권한이 없을 때 처리
            whereAreYou = "위치 권한이 없습니다."
        } catch (e: Exception) {
            whereAreYou = "오류가 발생했습니다: ${e.message}"
        }
    } else {
        whereAreYou = "위치 권한이 없습니다."
    }

    return whereAreYou
}

fun getCurrentAddress(context: Context): String? {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return null
    }


    val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

    if (location != null) {
        val latitude = 37.5510318//location.latitude//33.493246257239//36.815129//
        val longitude = 126.9411309//location.longitude//126.46173686782//127.1138939//
        val geocoder = Geocoder(context, Locale.getDefault())

        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!=null) {
                val address = addresses[0].getAddressLine(0)
                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName

                return "$address, $city, $state, $country, $postalCode, $knownName"
            }
            else
                return null
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    else{
        val latitude = 37.5510318//location.latitude//33.493246257239//36.815129//
        val longitude = 126.9411309//location.longitude//126.46173686782//127.1138939//
        val geocoder = Geocoder(context, Locale.getDefault())

        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!=null) {
                val address = addresses[0].getAddressLine(0)
                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName

                return "$address, $city, $state, $country, $postalCode, $knownName"
            }
            else
                return null
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    Log.e("ActivityCompat","null")

    return null
}