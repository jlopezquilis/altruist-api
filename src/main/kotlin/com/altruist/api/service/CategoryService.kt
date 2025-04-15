package com.altruist.api.service

import com.altruist.api.dto.category.GetCategoryResponse
import com.altruist.api.repository.CategoryRepository
import org.springframework.stereotype.Service


@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun getAllCategories(): List<GetCategoryResponse> {
        val categories = categoryRepository.findAll()

        if (categories.isEmpty()) {
            throw NoSuchElementException("No se encontraron categorías")
        }

        return categories.map {
            GetCategoryResponse(
                id_category = it.id_category,
                name = it.name,
                description = it.description,
                icon_url = it.icon_url
            )
        }
    }
}