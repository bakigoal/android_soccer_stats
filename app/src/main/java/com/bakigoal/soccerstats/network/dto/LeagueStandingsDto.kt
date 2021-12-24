package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueStandingsDto(
    val league: StandingsDto
)

data class StandingsDto(
    val id: Int,
    val name: String,
    val logo: String,
    val flag: String,
    val season: String,
    val standings: List<List<StandingDto>>
)

data class StandingDto(
    val rank: Int,
    val team: TeamDto,
    val points: Int,
    val form: String,
    // Promotion - Champions League (Group Stage)
    // Promotion - Champions League (Qualification)
    // Promotion - Europa League (Group Stage)
    // Promotion - Europa Conference League (Qualification)
    // null
    // Relegation - Championship
    // Premier League (Relegation)
    // Relegation - FNL
    val description: String?,
    val all: StandingStatsDto,
    val home: StandingStatsDto,
    val away: StandingStatsDto
)

data class StandingStatsDto(
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

