package com.altruist.api.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    val idUser: Long = 0,

    @Column(nullable = false)
    val name: String,

    val surname: String? = null,

    @Column(nullable = false, unique = true)
    val userName: String,

    @Column(nullable = false)
    val gender: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val passwordHash: String,

    val situation: String? = null,

    val profilePictureUrl: String? = null,

    @Column(nullable = false)
    val anonymous: Boolean
)
