package ru.hse.coursework.godaily.core.domain.service

import java.util.UUID

class UuidService {
    fun getUUIDFromString(id: String): UUID? {
        return try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}