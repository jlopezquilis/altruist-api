package com.altruist.api.model

import jakarta.persistence.*

@Entity
@Table(name = "post_image")
data class PostImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    val id_image: Long = 0,

    @Column(nullable = false)
    val url: String = "",

    @ManyToOne
    @JoinColumn(name = "id_post")
    val post: Post? = null
)
