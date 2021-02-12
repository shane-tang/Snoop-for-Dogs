package com.shanetang.data.api

data class SearchBody(
    val apikey: String,
    val resultStart: Int = 0,
    val objectType: String = "animals",
    val objectAction: String = "publicSearch",
    val filters: List<Filter>,
    val search: Search = Search(
        resultStart = resultStart.toString(),
        filters = filters,
    ),
)

data class Search(
    val resultStart: String,
    val resultLimit: String = "15",
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
