package com.altruist.api.controller
import com.altruist.api.dto.post.CreatePostRequest
import com.altruist.api.dto.post.GetPostResponse
import com.altruist.api.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping("/createPost")
    fun createPost(@RequestBody request: CreatePostRequest): ResponseEntity<Long> {
        val postId = postService.createPost(request)
        return ResponseEntity.ok(postId)
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<GetPostResponse> {
        val postResponse = postService.getPostById(id)
        return ResponseEntity.ok(postResponse)
    }

    @GetMapping("/filter")
    fun getPostsByFilters(
        @RequestParam idCategory: Long,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam maxDistanceKm: Double
    ): ResponseEntity<List<GetPostResponse>> {
        val posts = postService.getPostsByFilters(idCategory, latitude, longitude, maxDistanceKm)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/byUser/{idUser}")
    fun getPostsByUser(@PathVariable idUser: Long): ResponseEntity<List<GetPostResponse>> {
        val posts = postService.getPostsByUser(idUser)
        return ResponseEntity.ok(posts)
    }


}
