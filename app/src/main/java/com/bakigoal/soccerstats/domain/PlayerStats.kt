package com.bakigoal.soccerstats.domain

data class PlayerStats(
    val id: String,
    val year: Int,
    val leagueId: Int,
    val teamId: Int,
    val teamName: String,
    val teamLogo: String,
    val total: Int,
    val assists: Int,
    val saves: Int
)