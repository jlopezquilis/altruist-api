package com.altruist.api.dto.post

import java.time.LocalDateTime

data class GetPostResponse (
    val id_post: Long,
    val title: String,
    val description: String?,
    val status: String?,
    val quality: String?,
    val latitude: Double,
    val longitude: Double,
    val date_created: LocalDateTime,
    val categoryName: String?,
    val username: String?,
    val imageUrls: List<String> = emptyList()
)