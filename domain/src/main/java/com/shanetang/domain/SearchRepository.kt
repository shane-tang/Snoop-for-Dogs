package com.shanetang.domain

class SearchRepository {
    private val mapper = AnimalMapper()
    private val service = RescueServiceBuilder().buildService(RescueApi::class.java)

    // at the API layer we make a POST request, but really it's more reflective of a GET,
    // so internally we're calling it "get." i think future API versions are changing the
    // endpoint to a GET request
    suspend fun getDogs(apikey: String, filters: List<Filter>): SearchState {
        val response = service.postSearch(
            Body(
                apikey = apikey,
                filters = filters,
            )
        )
        return mapper.responseToState(response)
    }

}
