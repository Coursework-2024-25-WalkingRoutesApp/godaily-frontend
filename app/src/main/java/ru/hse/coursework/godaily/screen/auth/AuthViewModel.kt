package ru.hse.coursework.godaily.screen.auth

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.coursework.godaily.core.security.JwtManager
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val jwtManager: JwtManager
) : ViewModel() {

    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordAgain = mutableStateOf("")
    val selectedImageUri = mutableStateOf<Uri?>(null)


}