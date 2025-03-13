package ru.hse.coursework.godaily.core.domain.service

import java.util.UUID

class UuidService {
    fun getStringFromUUID(id: UUID): String {
        return id.toString()
    }

    fun getUUIDFromString(id: String): UUID? {
        return try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun getRandomUUID(): UUID {
        return UUID.randomUUID()
    }
}