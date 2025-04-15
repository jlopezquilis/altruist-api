package com.altruist.api.dto.post

import com.altruist.api.dto.category.GetCategoryResponse
import com.altruist.api.dto.user.GetUserResponse
import java.time.LocalDateTime

data class GetPostResponse(
    val id_post: Long,
    val title: String,
    val description: String?,
    val status: String?,
    val quality: String?,
    val latitude: Double,
    val longitude: Double,
    val date_created: LocalDateTime,
    val category: GetCategoryResponse,
    val user: GetUserResponse,
    val imageUrls: List<String>,
    val distanceFromFilter: Double
)
