package com.altruist.api.dto

data class AllCategoriesResponse(
    val id_category: Long,
    val name: String,
    val description: String?,
    val icon_url: String?
)