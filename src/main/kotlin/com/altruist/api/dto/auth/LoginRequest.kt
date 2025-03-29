package com.altruist.api.dto.auth

data class LoginRequest(
    val email: String,
    val password_hash: String
)
