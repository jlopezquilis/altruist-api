package com.altruist.api.service

import com.altruist.api.dto.auth.LoginRequest
import com.altruist.api.dto.auth.LoginResponse
import com.altruist.api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado con el email: ${request.email}") }

        if (user.password_hash != request.password_hash) {
            throw IllegalArgumentException("Contraseña incorrecta.")
        }

        return LoginResponse(
            message = "Login correcto",
            id_user = user.id_user,
            name = user.name,
            surname = user.surname ?: "",
            username = user.username,
            gender = user.gender,
            email = user.email,
            password_hash = user.password_hash,
            situation = user.situation ?: "",
            profile_picture_url = user.profile_picture_url ?: "",
            anonymous = user.anonymous
        )
    }
}
