package com.altruist.api.controller

import com.altruist.api.dto.user.AvailabilityResponse
import com.altruist.api.dto.user.CreateUserRequest
import com.altruist.api.dto.user.LoginRequest
import com.altruist.api.dto.user.LoginResponse
import com.altruist.api.dto.user.SendVerificationCodeRequest
import com.altruist.api.model.User
import com.altruist.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
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

    @GetMapping("/check-email")
    fun checkEmailExists(@RequestParam email: String): ResponseEntity<AvailabilityResponse> {
        val exists = userService.existByEmail(email)
        return ResponseEntity.ok(AvailabilityResponse(exists))
    }

    @GetMapping("/check-username")
    fun checkUsernameExists(@RequestParam username: String): ResponseEntity<AvailabilityResponse> {
        val exists = userService.existByUsername(username)
        return ResponseEntity.ok(AvailabilityResponse(exists))
    }

    @PostMapping("/create")
    fun createUser(@RequestBody user: CreateUserRequest): ResponseEntity<User> {
        val savedUser = userService.createUser(user)
        return ResponseEntity.ok(savedUser)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        return try {
            val user = userService.getUserById(id)
            ResponseEntity.ok(user)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

}
