package com.altruist.api.service

import com.altruist.api.model.request.Request
import com.altruist.api.model.request.RequestId
import com.altruist.api.repository.RequestRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RequestService(
    private val requestRepository: RequestRepository
) {

    fun createRequest(request: Request): Request {
        return requestRepository.save(request)
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
