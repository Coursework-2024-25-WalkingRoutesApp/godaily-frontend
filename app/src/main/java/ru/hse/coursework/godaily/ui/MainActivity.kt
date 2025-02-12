package ru.hse.coursework.godaily.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.AndroidEntryPoint
import ru.hse.coursework.godaily.BuildConfig
import ru.hse.coursework.godaily.ui.components.superorganisms.YandexMapNavigationView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val locationPermissionGranted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (locationPermissionGranted) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            //TODO увед, что приложение работает только с геолокацией
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        setContent {
            DisposableEffect(Unit) {
                MapKitFactory.getInstance().onStart()

                onDispose {
                    MapKitFactory.getInstance().onStop()
                }
            }
            //TODO
            val testPoints = listOf(
                Point(55.6600, 37.5100), // Юго-Западная окраина Москвы
                Point(55.6780, 37.5250),
                Point(55.6900, 37.5450),
                Point(55.7050, 37.5600),
                Point(55.7200, 37.5800),
                Point(55.7350, 37.6000),
                Point(55.7450, 37.6100),
                Point(55.7550, 37.6200), // Центр Москвы
                Point(55.7600, 37.6250),
                Point(55.7700, 37.6300)  // Финальная точка ближе к северу
            )

            val passedPoints = remember { mutableStateListOf<Point>() }

            YandexMapNavigationView(
                routePoints = testPoints,
                passedPoints = passedPoints
            )
            //MainScreen()
        }

        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }
}
