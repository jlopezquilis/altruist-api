package com.altruist.api.service

import com.altruist.api.dto.category.GetCategoryResponse
import com.altruist.api.dto.post.CreatePostRequest
import com.altruist.api.dto.post.GetPostResponse
import com.altruist.api.dto.user.GetUserResponse
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
            category = GetCategoryResponse(
                id_category = post.category!!.id_category,
                name = post.category.name,
                description = post.category.description,
                icon_url = post.category.icon_url
            ),
            user = GetUserResponse(
                id_user = post.user!!.idUser,
                name = post.user.name,
                surname = post.user.surname,
                username = post.user.username,
                gender = post.user.gender,
                email = post.user.email,
                password_hash = post.user.password_hash,
                situation = post.user.situation,
                profile_picture_url = post.user.profile_picture_url,
                anonymous = post.user.anonymous
            ),
            imageUrls = post.images.map { it.url },
            distanceFromFilter = 0.0
        )
    }

    //Formula de haversine
    fun calculateDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val earthRadiusKm = 6371.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadiusKm * c
    }

    @Transactional(readOnly = true)
    fun getPostsByFilters(
        idCategory: Long,
        latitude: Double,
        longitude: Double,
        maxDistanceKm: Double
    ): List<GetPostResponse> {

        val posts = postRepository.findAll()
        var distance = 0.0

        return posts
            .filter { it.category?.id_category == idCategory }
            .filter {
                distance = calculateDistance(
                    latitude,
                    longitude,
                    it.latitude,
                    it.longitude
                )
                distance <= maxDistanceKm
            }
            .map { post ->
                GetPostResponse(
                    id_post = post.id_post,
                    title = post.title,
                    description = post.description,
                    status = post.status,
                    quality = post.quality,
                    latitude = post.latitude,
                    longitude = post.longitude,
                    date_created = post.date_created,
                    category = GetCategoryResponse(
                        id_category = post.category!!.id_category,
                        name = post.category.name,
                        description = post.category.description,
                        icon_url = post.category.icon_url
                    ),
                    user = GetUserResponse(
                        id_user = post.user!!.idUser,
                        name = post.user.name,
                        surname = post.user.surname,
                        username = post.user.username,
                        gender = post.user.gender,
                        email = post.user.email,
                        password_hash = post.user.password_hash,
                        situation = post.user.situation,
                        profile_picture_url = post.user.profile_picture_url,
                        anonymous = post.user.anonymous
                    ),
                    imageUrls = post.images.map { it.url },
                    distanceFromFilter = distance
                )
            }
    }

    @Transactional(readOnly = true)
    fun getPostsByUser(idUser: Long): List<GetPostResponse> {
        val user = userRepository.findById(idUser)
            .orElseThrow { RuntimeException("Usuario no encontrado") }

        val posts = postRepository.findAllByUser_IdUser(idUser)

        return posts.map { post ->
            GetPostResponse(
                id_post = post.id_post,
                title = post.title,
                description = post.description,
                status = post.status,
                quality = post.quality,
                latitude = post.latitude,
                longitude = post.longitude,
                date_created = post.date_created,
                category = GetCategoryResponse(
                    id_category = post.category!!.id_category,
                    name = post.category.name,
                    description = post.category.description,
                    icon_url = post.category.icon_url
                ),
                user = GetUserResponse(
                    id_user = user.idUser,
                    name = user.name,
                    surname = user.surname,
                    username = user.username,
                    gender = user.gender,
                    email = user.email,
                    password_hash = user.password_hash,
                    situation = user.situation,
                    profile_picture_url = user.profile_picture_url,
                    anonymous = user.anonymous
                ),
                imageUrls = post.images.map { it.url },
                distanceFromFilter = 0.0
            )
        }
    }


}
