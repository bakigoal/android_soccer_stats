package com.bakigoal.soccerstats.database.entity

import androidx.room.Entity

@Entity(tableName = "seasons", primaryKeys = ["leagueId", "year"])
data class SeasonEntity(
    val year: String,
    val leagueId: Int,
    val start: String,
    val end: String,
    val current: Boolean,
)