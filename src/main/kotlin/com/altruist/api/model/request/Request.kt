package com.altruist.api.model.request

import com.altruist.api.model.Post
import com.altruist.api.model.request.RequestId
import com.altruist.api.model.User
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "request")
data class Request(
    @EmbeddedId
    val id: RequestId = RequestId(),

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "id_user", nullable = false)
    val user: User = User(),

    @ManyToOne
    @MapsId("post")
    @JoinColumn(name = "id_post", nullable = false)
    val post: Post = Post(),

    @Column(nullable = false, length = 255)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val body: String? = null,

    @Column(length = 255)
    val image: String? = null,

    @Column(length = 50)
    val status: String? = null,

    @Column(name = "date_created", nullable = false)
    val dateCreated: LocalDateTime = LocalDateTime.now()
)
