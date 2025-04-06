package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDto(
    @JsonProperty("username")
    val username: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("photoUrl")
    val photoURL: String?
)
