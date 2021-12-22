package com.bakigoal.soccerstats.database.entity

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "standing_teams", primaryKeys = ["leagueSeasonId", "teamId"])
data class StandingTeamEntity(
    val leagueSeasonId: String,
    val rank: Int,
    val points: Int,
    val form: String,
    val teamId: Int,
    val teamName: String,
    val teamLogo: String,
    val description: String?,
    @Embedded(prefix = "all_")
    val all: StatsEmbedded,
    @Embedded(prefix = "home_")
    val home: StatsEmbedded,
    @Embedded(prefix = "away_")
    val away: StatsEmbedded
)

data class StatsEmbedded(
    val played: Int,
    val win: Int,
    val draw: Int,
    val lose: Int,
    val goalsFor: Int,
    val goalsAgainst: Int
)