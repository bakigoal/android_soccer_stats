package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerInfoDto(
    val player: PlayerDto,
    val statistics: List<PlayerStatsDto>
)

data class PlayerStatsDto(
    val team: TeamDto,
    val league: PlayerLeagueDto,
    val goals: GoalsDto
)

data class PlayerLeagueDto(
    val id: Int,
    val name: String,
    val logo: String,
    val season: Int
)

data class GoalsDto(
    val total: Int? = 0,
    val assists: Int? = 0,
    val saves: Int? = 0
)
