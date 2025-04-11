package com.altruist.api.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    val id_post: Long = 0,

    @Column(nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    val status: String? = null,

    val quality: String? = null,

    @Column(nullable = false)
    val latitude: Double = 0.0,

    @Column(nullable = false)
    val longitude: Double = 0.0,

    @Column(name = "date_created", nullable = false)
    val date_created: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "id_category")
    val category: Category? = null,

    @ManyToOne
    @JoinColumn(name = "id_user")
    val user: User? = null,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val images: MutableList<PostImage> = mutableListOf()
)
