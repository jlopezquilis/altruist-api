package com.altruist.api.model

import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    val id_user: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    val surname: String? = null,

    @Column(nullable = false, unique = true)
    val username: String = "",

    @Column(nullable = false)
    val gender: String = "",

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val password_hash: String = "",

    val situation: String? = null,

    val profile_picture_url: String? = null,

    @Column(nullable = false)
    val anonymous: Boolean = false
)
