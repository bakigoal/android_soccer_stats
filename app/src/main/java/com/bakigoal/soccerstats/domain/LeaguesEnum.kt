package com.bakigoal.soccerstats.domain

enum class LeaguesEnum(val order: Int, val id: Int) {
    PREMIER_LEAGUE_ID(1, 39),
    LA_LIGA_ID(2, 140),
    SERIE_A_ID(3, 135),
    BUNDESLIGA_ID(4, 78),
    LEAGUE_1_ID(5, 61),
    RPL_ID(6, 235);

    companion object {
        fun fromId(id: Int): LeaguesEnum = values().first { id == it.id }
    }
}