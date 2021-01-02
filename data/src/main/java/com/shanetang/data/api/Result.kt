package com.shanetang.data.api

import com.shanetang.data.AnimalResponse

data class Result(
    val status: String,
    val message: String?,
    val data: Map<String, AnimalResponse>?,
    val foundRows: Int?,
)
