package com.shanetang.domain

import retrofit2.Response
import retrofit2.http.GET

data class Result(val id: Int, val userId: Int, val title: String, val completed: Boolean)
data class Todo(val results: List<Result>)

interface RescueApi {
    @GET("/todos/1")
    suspend fun getTodos(): Response<Result>
}
