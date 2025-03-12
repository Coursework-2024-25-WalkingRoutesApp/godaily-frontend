package ru.hse.coursework.godaily.core.domain.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.hse.coursework.godaily.core.data.model.UserCoordinateDto
import kotlin.coroutines.resume

class LocationService(context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Point? = suspendCancellableCoroutine { cont ->
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                cont.resume(Point(it.latitude, it.longitude))
            } ?: cont.resume(null)
        }.addOnFailureListener {
            cont.resume(null)
        }
    }

    suspend fun getUserCoordinate(): UserCoordinateDto {
        val currentLocation = getCurrentLocation()
            ?: return UserCoordinateDto(
                null,
                null
            )
        return UserCoordinateDto(
            currentLocation.latitude,
            currentLocation.longitude
        )
    }
}
