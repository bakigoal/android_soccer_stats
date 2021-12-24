package com.bakigoal.soccerstats.domain

data class PlayerInfo(
    val player: Player,
    val playerStats: PlayerStats,
    var scorerPosition: Int = -1
)