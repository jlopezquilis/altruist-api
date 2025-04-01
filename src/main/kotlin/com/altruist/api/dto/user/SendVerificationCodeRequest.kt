package com.altruist.api.dto.user

data class SendVerificationCodeRequest(
    val email: String,
    val code: String
)
