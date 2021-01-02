package com.shanetang.domain

data class Body(
    val apikey: String,
    val objectType: String = "animals",
    val objectAction: String = "publicSearch",
    val filters: List<Filter>,
    val search: Search = Search(filters = filters),
)

data class Search(
    val resultStart: String = "0",
    val resultLimit: String = "50",
    val resultSort: String = "animalUpdatedDate", // TODO this should be user-defined
    val resultOrder: String = "desc",
    val calcFoundRows: String = "yes",
    val filters: List<Filter>,
    val fields: List<String> = listOf(
        "animalName",
        "animalBirthdate",
        "animalPictures",
        "animalLocationDistance",
    ),
)

data class Filter(
    val fieldName: String,
    val operation: String,
    val criteria: String,
)
