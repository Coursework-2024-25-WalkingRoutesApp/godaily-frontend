package ru.hse.coursework.godaily.core.domain.service

import android.net.Uri
import javax.inject.Inject

class PhotoConverterService @Inject constructor() {
    fun urlToUri(url: String?): Uri? {
        return try {
            url?.let { Uri.parse(it) }
        } catch (e: Exception) {
            return null
        }
    }
}