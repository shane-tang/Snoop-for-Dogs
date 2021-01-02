package com.shanetang.domain

open class SearchState {

    object Loading : SearchState()

    data class Successful(
        val animals: List<Animal>,
        val count: Int?,
    ) : SearchState()

    data class Error(val message: String) : SearchState()
}
