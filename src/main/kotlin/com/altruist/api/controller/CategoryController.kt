package com.altruist.api.controller

import com.altruist.api.dto.AllCategoriesResponse
import com.altruist.api.repository.CategoryRepository
import com.altruist.api.service.CategoryService
import com.altruist.api.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías de donaciones")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve la lista completa de categorías disponibles")
    fun getAllCategories(): ResponseEntity<List<AllCategoriesResponse>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }
}