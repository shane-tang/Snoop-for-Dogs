package com.shanetang.domain.models

open class SearchResults {

    data class Successful(
        val animals: List<Animal>,
        val count: Int,
    ) : SearchResults()

    data class Error(val message: String) : SearchResults()
}
