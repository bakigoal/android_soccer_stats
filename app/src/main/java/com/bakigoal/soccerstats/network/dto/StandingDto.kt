package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandingDto(
    val rank: Int,
    val team: StandingTeamDto,
    val points: Int,
    // Promotion - Champions League (Group Stage)
    // Promotion - Champions League (Qualification)
    // Promotion - Europa League (Group Stage)
    // Promotion - Europa Conference League (Qualification)
    // null
    // Relegation - Championship
    // Premier League (Relegation)
    // Relegation - FNL
    val description: String?,
    val all: StandingAllDto,
    val home: StandingAllDto,
    val away: StandingAllDto
)

data class StandingAllDto(
    val played: Int,
    val win: Int,
    val draw: Int,
    val lose: Int,
    val goals: StandingGoalsDto,
)

data class StandingGoalsDto(
    @Json(name = "for")
    val goalsFor: Int,
    @Json(name = "against")
    val goalsAgainst: Int
)

data class StandingTeamDto(
    val id: Int,
    val name: String,
    val logo: String
)
