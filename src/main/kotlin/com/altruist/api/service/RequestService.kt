package com.altruist.api.service

import com.altruist.api.dto.request.CreateSimplifiedRequestRequest
import com.altruist.api.model.request.Request
import com.altruist.api.model.request.RequestId
import com.altruist.api.repository.PostRepository
import com.altruist.api.repository.RequestRepository
import com.altruist.api.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class RequestService(
    private val requestRepository: RequestRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {

    fun createRequest(dto: CreateSimplifiedRequestRequest): Boolean {
        val id = RequestId(dto.id_user, dto.id_post)

        if (requestRepository.existsById(id)) {
            throw IllegalStateException("Ya has solicitado este post")
        }

        val user = userRepository.findById(dto.id_user)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        val post = postRepository.findById(dto.id_post)
            .orElseThrow { IllegalArgumentException("Post no encontrado") }

        val request = Request(
            id = id,
            user = user,
            post = post,
            title = "",
            body = null,
            image = null,
            status = dto.status,
            dateCreated = LocalDateTime.now()
        )

        requestRepository.save(request)

        return true
    }


    fun getRequestById(id: RequestId): Optional<Request> {
        return requestRepository.findById(id)
    }

    fun getRequestsByUser(idUser: Long): List<Request> {
        return requestRepository.findByIdUser(idUser)
    }

    fun getRequestsByPost(idPost: Long): List<Request> {
        return requestRepository.findByIdPost(idPost)
    }

    fun getRequestByUserAndPost(idUser: Long, idPost: Long): Optional<Request> {
        return requestRepository.findByIdUserAndIdPost(idUser, idPost)
    }

    fun deleteRequest(id: RequestId) {
        requestRepository.deleteById(id)
    }
}
