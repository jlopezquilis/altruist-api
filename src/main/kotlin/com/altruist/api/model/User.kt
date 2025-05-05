package com.altruist.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @JsonProperty("id_user")
    val idUser: Long = 0,
    //TODO: Modificar todas las entidades JPA para evitar underscores.

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
