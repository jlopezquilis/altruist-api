package com.altruist.api.repository
import com.altruist.api.model.PostImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostImageRepository : JpaRepository<PostImage, Long> {
}