package com.bakigoal.soccerstats.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "squad_team")
data class SquadTeamEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val logo: String,
)

@Entity(tableName = "squad_players")
data class SquadPlayerEntity(
    @PrimaryKey
    val id: Int,
    val teamId: Int,
    val name: String,
    val age: Int,
    val number: Int?,
    val position: String,
    val photo: String
)

data class SquadDB(
    @Embedded
    val team: SquadTeamEntity,

    @Relation(parentColumn = "id", entityColumn = "teamId")
    val players: List<SquadPlayerEntity>
)