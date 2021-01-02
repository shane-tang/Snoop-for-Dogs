package com.shanetang.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RescueServiceBuilder {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.rescuegroups.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> buildService(service: Class<T>): T = retrofit.create(service)
}
