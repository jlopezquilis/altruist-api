package com.altruist.api.service

import com.altruist.api.dto.post.CreatePostRequest
import com.altruist.api.dto.post.GetPostResponse
import com.altruist.api.model.Post
import com.altruist.api.model.PostImage
import com.altruist.api.repository.CategoryRepository
import com.altruist.api.repository.PostRepository
import com.altruist.api.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    fun createPost(request: CreatePostRequest): Long {
        val user = userRepository.findById(request.id_user)
            .orElseThrow { RuntimeException("User not found") }

        val category = categoryRepository.findById(request.id_category)
            .orElseThrow { RuntimeException("Category not found") }

        val post = Post(
            title = request.title,
            description = request.description,
            status = request.status,
            quality = request.quality,
            latitude = request.latitude,
            longitude = request.longitude,
            date_created = LocalDateTime.now(),
            user = user,
            category = category
        )

        request.imageUrls.forEach { imageUrl ->
            post.images.add(PostImage(url = imageUrl, post = post))
        }

        val savedPost = postRepository.save(post)

        return savedPost.id_post
    }

    @Transactional(readOnly = true)
    fun getPostById(id: Long): GetPostResponse {
        val post = postRepository.findById(id)
            .orElseThrow { RuntimeException("Post not found") }

        return GetPostResponse(
            id_post = post.id_post,
            title = post.title,
            description = post.description,
            status = post.status,
            quality = post.quality,
            latitude = post.latitude,
            longitude = post.longitude,
            date_created = post.date_created,
            categoryName = post.category?.name,
            username = post.user?.username,
            imageUrls = post.images.map { it.url }
        )
    }
}
