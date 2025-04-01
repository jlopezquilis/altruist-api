package com.altruist.api.service

import com.altruist.api.dto.user.LoginRequest
import com.altruist.api.dto.user.LoginResponse
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

    fun sendVerificationCode(toEmail: String, code: String) {
        val message = SimpleMailMessage().apply {
            setFrom("Altruist <altruist.noreply@gmail.com>") // 👈 Nombre + correo
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


}
