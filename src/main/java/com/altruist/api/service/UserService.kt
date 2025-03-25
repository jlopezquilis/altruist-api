package com.altruist.api.service

import com.altruist.api.dto.LoginRequest
import com.altruist.api.dto.LoginResponse
import com.altruist.api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado con el email: ${request.email}") }

        if (user.passwordHash != request.password) {
            throw IllegalArgumentException("Contraseña incorrecta.")
        }

        return LoginResponse(
            message = "Login correcto",
            userId = user.idUser,
            name = user.name
        )
    }
}
