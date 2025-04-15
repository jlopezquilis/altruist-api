package com.altruist.api.dto.category

data class GetCategoryResponse(
    val id_category: Long,
    val name: String,
    val description: String?,
    val icon_url: String?
)