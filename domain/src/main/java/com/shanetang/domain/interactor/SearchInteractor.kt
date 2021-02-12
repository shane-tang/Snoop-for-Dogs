package com.shanetang.domain.interactor

import com.shanetang.data.api.Filter
import com.shanetang.domain.models.SearchResults
import com.shanetang.data.repository.SearchRepository

class SearchInteractor {
    private val repository = SearchRepository()
    private val mapper = AnimalMapper()

    suspend fun searchAnimals(
            apikey: String,
            resultStart: Int,
            zipcode: String,
    ) : SearchResults {
        val filters = baseFilters + listOf(
            Filter("animalLocation", "equals", zipcode)
        )
        val response = repository.postAnimals(apikey, resultStart, filters)
        return mapper.responseToResults(response)
    }

    companion object {
        private val baseFilters = listOf(
            Filter("animalStatus", "notequal", "adopted"),
            Filter("animalSpecies", "equals", "dog"),
            Filter("animalLocationDistance", "radius", "10"),
        )
    }
}
