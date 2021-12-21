package com.bakigoal.soccerstats.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val name: String,
    val leagueId: Int,
    val code: String?,
    val flag: String?
)