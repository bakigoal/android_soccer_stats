package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerInfoDto(
    val player: PlayerDto,
    val statistics: PlayerStatsDto
)

data class PlayerStatsDto(
    val team: TeamDto,
    val goals: GoalsDto
)

data class GoalsDto(
    val total: Int,
    val assists: Int,
    val saves: Int
)
