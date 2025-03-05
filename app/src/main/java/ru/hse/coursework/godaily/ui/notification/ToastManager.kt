package ru.hse.coursework.godaily.ui.notification

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun ComposeToastManager(): ToastManager {
    val context = LocalContext.current
    return remember { ToastManager(context) }
}

class ToastManager(private val context: Context) {
    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}
