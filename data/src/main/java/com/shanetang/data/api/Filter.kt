package com.shanetang.data.api

data class Filter(
    val fieldName: String,
    val operation: String,
    val criteria: String,
)
