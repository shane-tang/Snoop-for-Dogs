package com.shanetang.domain

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class Result(
    val status: String,
    val message: String?,
    val data: Map<String, AnimalResponse>?,
    val foundRows: Int?,
)

interface RescueApi {
    @POST("/http/v2.json")
    suspend fun postSearch(
        @Body body: com.shanetang.domain.Body
    ): Response<Result>
}
