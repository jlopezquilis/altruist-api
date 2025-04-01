package com.altruist.api.controller

import com.altruist.api.dto.user.LoginRequest
import com.altruist.api.dto.user.LoginResponse
import com.altruist.api.dto.user.SendVerificationCodeRequest
import com.altruist.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones relacionadas con login y seguridad")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Devuelve información del usuario si las credenciales son válidas")

    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val response = userService.login(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/send-verification-code")
    fun sendVerificationCode(@RequestBody request: SendVerificationCodeRequest): ResponseEntity<String> {
        return try {
            userService.sendVerificationCode(request.email, request.code)
            ResponseEntity.ok("Código enviado con éxito")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al enviar el código: ${e.message}")
        }
    }
}
