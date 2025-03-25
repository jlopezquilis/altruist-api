package com.altruist.api.controller

import com.altruist.api.dto.LoginRequest
import com.altruist.api.dto.LoginResponse
import com.altruist.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones relacionadas con login y seguridad")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Devuelve información del usuario si las credenciales son válidas")

    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val response = userService.login(request)
        return ResponseEntity.ok(response)
    }
}
