package com.bakigoal.soccerstats.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    val id: String,
    val playerId: Int,
    val year: Int,
    val leagueId: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    @Embedded(prefix = "birth_")
    val birth: BirthEntity,
    val nationality: String,
    val height: String?,
    val weight: String?,
    val injured: Boolean? = false,
    val photo: String
)

data class BirthEntity(
    val date: String,
    val place: String?,
    val country: String?
)

data class PlayerInfoDB(
    @Embedded
    val player: PlayerEntity,

    @Relation(parentColumn = "id", entityColumn = "id")
    val statistics: PlayerStatsEntity
)