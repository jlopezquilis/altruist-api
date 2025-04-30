package com.altruist.api.model.request

import java.io.Serializable
import jakarta.persistence.Embeddable

@Embeddable
data class RequestId(
    val user: Long = 0,
    val post: Long = 0
) : Serializable
