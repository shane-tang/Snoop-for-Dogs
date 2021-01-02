package com.shanetang.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RescueApi {
    @POST("/http/v2.json")
    suspend fun postSearch(
        @Body body: SearchBody
    ): Response<Result>
}
