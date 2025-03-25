package com.altruist.api.dto

data class LoginResponse(
    val message: String,
    val userId: Long,
    val name: String
)