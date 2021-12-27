package com.bakigoal.soccerstats.domain

data class Squad(
    val team: SquadTeam,
    val players: List<SquadPlayer>
)

data class SquadTeam(
    val id: Int,
    val name: String,
    val logo: String,
)

data class SquadPlayer(
    val id: Int,
    val name: String,
    val age: Int,
    val number: Int?,
    val position: String,
    val photo: String
) {
    fun numberText() = if (number != null) "$number" else "-"
    fun ageText() = "$age"
}
