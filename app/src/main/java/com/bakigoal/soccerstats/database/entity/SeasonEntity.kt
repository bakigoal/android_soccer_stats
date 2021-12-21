package com.bakigoal.soccerstats.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seasons")
data class SeasonEntity (
    @PrimaryKey
    val year: String,
    val leagueId: Int,
    val start: String,
    val end: String,
    val current: Boolean,
)