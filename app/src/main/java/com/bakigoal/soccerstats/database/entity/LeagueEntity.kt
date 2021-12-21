package com.bakigoal.soccerstats.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "leagues")
data class LeagueEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val logo: String
)

data class LeagueWithCountryAndSeasons(

    @Embedded
    val league: LeagueEntity,

    @Relation(parentColumn = "id", entityColumn = "leagueId")
    val country: CountryEntity,

    @Relation(parentColumn = "id", entityColumn = "leagueId")
    val seasons: List<SeasonEntity>
)