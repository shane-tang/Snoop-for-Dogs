package com.shanetang.domain.models

data class Animal(
    val id: String,
    val name: String,
    val pictures: List<AnimalPicture>?,
    val distance: Int?,
    val age: String?,
)

data class AnimalPicture(
    val urlFullsize: String?,
    val urlThumbnail: String?,
)
