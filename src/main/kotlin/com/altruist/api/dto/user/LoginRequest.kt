package com.altruist.api.dto.user

data class LoginRequest(
    val email: String,
    val password_hash: String
)
