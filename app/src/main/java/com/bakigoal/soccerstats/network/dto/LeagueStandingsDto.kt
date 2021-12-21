package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueStandingsDto(
    val id: Int,
    val name: String,
    val type: String,
    val logo: String,
    val flag: String,
    val season: String,
    val standings: List<StandingDto>
)