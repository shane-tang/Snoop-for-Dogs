package com.shanetang.data.repository

import com.shanetang.data.RescueServiceBuilder
import com.shanetang.data.api.Filter
import com.shanetang.data.api.RescueApi
import com.shanetang.data.api.Result
import com.shanetang.data.api.SearchBody
import retrofit2.Response

class SearchRepository {
    private val service = RescueServiceBuilder()
        .buildService(RescueApi::class.java)

    suspend fun postAnimals(
        apikey: String,
        resultStart: Int,
        filters: List<Filter>
    ): Response<Result> = service.postSearch(
        SearchBody(
            apikey = apikey,
            resultStart = resultStart,
            filters = filters,
        )
    )
}
