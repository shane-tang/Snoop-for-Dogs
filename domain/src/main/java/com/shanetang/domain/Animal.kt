package com.shanetang.domain

data class Animal(
    val id: String,
    val name: String,
    val pictures: List<AnimalPicture>?,
    val distance: Int?,
    val age: String?,
)
