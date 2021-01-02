package com.shanetang.data

data class AnimalResponse(
    val animalID: String,
    val animalName: String,
    val animalLocation: String?,
    val animalPictures: List<AnimalPicture>?,
    val animalLocationDistance: Int?,
    val animalBirthdate: String?,
)

data class AnimalPicture(
    val urlSecureFullsize: String?,
    val urlSecureThumbnail: String?,
)
