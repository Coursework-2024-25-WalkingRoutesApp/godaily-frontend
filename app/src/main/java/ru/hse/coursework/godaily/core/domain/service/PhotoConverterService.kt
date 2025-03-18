package ru.hse.coursework.godaily.core.domain.service

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject

class PhotoConverterService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun urlToUri(url: String?): Uri? {
        return try {
            url?.let { Uri.parse(it) }
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun uriToByteArray(uri: Uri): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                inputStream?.use {
                    //TODO
                    println(it.readBytes())
                    it.readBytes()
                }
            } catch (e: Exception) {
                null
            }
        }
    }

}