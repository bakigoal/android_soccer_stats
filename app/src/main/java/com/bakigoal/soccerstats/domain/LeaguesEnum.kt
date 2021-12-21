package com.bakigoal.soccerstats.domain

enum class LeaguesEnum(val order: Int, val id: Int) {
    RPL_ID(1, 235),
    PREMIER_LEAGUE_ID(2, 39),
    LA_LIGA_ID(3, 140),
    BUNDESLIGA_ID(4, 78),
    SERIE_A_ID(5, 135),
    LEAGUE_1_ID(6, 61);

    companion object {
        fun fromId(id: Int): LeaguesEnum = values().first { id == it.id }
    }
}