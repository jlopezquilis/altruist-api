package com.altruist.api.model

import jakarta.persistence.*

@Entity
@Table(name = "Category")
data class Category (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    val id_category : Long = 0,

    @Column(nullable = false)
    val name : String = "",

    val description : String = "",

    val icon_url : String = ""
)
