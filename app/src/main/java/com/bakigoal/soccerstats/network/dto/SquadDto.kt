package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SquadDto(
    val team: SquadTeamDto,
    val players: List<SquadPlayerDto>
)

data class SquadTeamDto(
    val id: Int,
    val name: String,
    val logo: String,
)

data class SquadPlayerDto(
    val id: Int,
    val name: String,
    val age: Int,
    val number: Int?,
    val position: String,
    val photo: String
)
