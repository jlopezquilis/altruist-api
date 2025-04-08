package com.altruist.api.service

import com.altruist.api.dto.AllCategoriesResponse
import com.altruist.api.repository.CategoryRepository
import com.altruist.api.repository.UserRepository
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun getAllCategories(): List<AllCategoriesResponse> {
        val categories = categoryRepository.findAll()

        if (categories.isEmpty()) {
            throw NoSuchElementException("No se encontraron categorías")
        }

        return categories.map {
            AllCategoriesResponse(
                id_category = it.id_category,
                name = it.name,
                description = it.description,
                icon_url = it.icon_url
            )
        }
    }
}