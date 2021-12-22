package com.bakigoal.soccerstats.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "standings")
data class StandingsLeagueEntity(
    @PrimaryKey
    val leagueSeasonId: String,
    val leagueName: String,
    val leagueType: String,
    val leagueLogo: String,
    val countryFlag: String
)

data class StandingsDB(
    @Embedded
    val leagueInfo: StandingsLeagueEntity,

    @Relation(parentColumn = "leagueSeasonId", entityColumn = "leagueSeasonId")
    val standings: List<StandingTeamEntity>
    )