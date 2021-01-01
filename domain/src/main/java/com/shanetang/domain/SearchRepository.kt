package com.shanetang.domain

import retrofit2.Response


class SearchRepository {
    private val service = RescueServiceBuilder().buildService(RescueApi::class.java)

    suspend fun getTodos(): Response<Result> {
        val response = service.getTodos()
        return response
    }

}
