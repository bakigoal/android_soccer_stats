package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDto(
    val id: Int,
    val name: String,
    val logo: String
)