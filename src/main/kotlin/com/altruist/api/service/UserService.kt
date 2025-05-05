package com.altruist.api.service

import com.altruist.api.dto.user.CreateUserRequest
import com.altruist.api.dto.user.LoginRequest
import com.altruist.api.dto.user.LoginResponse
import com.altruist.api.model.User
import com.altruist.api.repository.UserRepository
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mailSender: JavaMailSender
) {

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado con el email: ${request.email}") }

        if (user.password_hash != request.password_hash) {
            throw IllegalArgumentException("Contraseña incorrecta.")
        }

        return LoginResponse(
            message = "Login correcto",
            id_user = user.idUser,
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

    fun sendVerificationCode(toEmail: String, code: String) {
        val message = SimpleMailMessage().apply {
            setFrom("Altruist <altruist.noreply@gmail.com>")
            setTo(toEmail)
            subject = "Código de verificación para completar tu registro en Altruist"
            text = """
            ¡Hola!

            Gracias por registrarte en Altruist. Para completar tu registro, por favor introduce el siguiente código de verificación en la aplicación:

            Código: $code

            Este código es válido durante los próximos 10 minutos. Si no has solicitado este registro, puedes ignorar este mensaje.

            ¡Gracias por formar parte de Altruist!

            —
            Equipo Altruist
        """.trimIndent()
        }
        mailSender.send(message)
    }

    fun createUser(request: CreateUserRequest): User {
        val newUser = User(
            name = request.name,
            surname = request.surname,
            username = request.username,
            gender = request.gender,
            email = request.email,
            password_hash = request.password_hash,
            situation = request.situation,
            profile_picture_url = request.profile_picture_url,
            anonymous = request.anonymous
        )
        return userRepository.save(newUser)
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NoSuchElementException("Usuario no encontrado con el id: $id") }
    }

    fun existByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun existByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }


}
