package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDto<T>(
    val get: String,
    val parameters: List<Any>,
    val errors: List<Any>,
    val results: Int,
    val response: List<T>
)
