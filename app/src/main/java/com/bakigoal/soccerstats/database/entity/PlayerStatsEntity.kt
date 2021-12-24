package com.bakigoal.soccerstats.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_stats")
data class PlayerStatsEntity(
    @PrimaryKey
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