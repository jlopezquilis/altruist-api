package com.altruist.api.repository

import com.altruist.api.model.request.RequestId
import com.altruist.api.model.request.Request
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RequestRepository : JpaRepository<Request, RequestId> {
    fun findByIdUser(userId: Long): List<Request>
    fun findByIdPost(postId: Long): List<Request>
    fun findByIdUserAndIdPost(userId: Long, postId: Long): Optional<Request>
}
