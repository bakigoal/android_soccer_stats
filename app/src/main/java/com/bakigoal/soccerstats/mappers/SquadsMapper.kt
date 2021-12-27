package com.bakigoal.soccerstats.mappers

import com.bakigoal.soccerstats.database.entity.SquadDB
import com.bakigoal.soccerstats.database.entity.SquadPlayerEntity
import com.bakigoal.soccerstats.database.entity.SquadTeamEntity
import com.bakigoal.soccerstats.domain.Squad
import com.bakigoal.soccerstats.domain.SquadPlayer
import com.bakigoal.soccerstats.domain.SquadTeam
import com.bakigoal.soccerstats.network.dto.SquadDto
import com.bakigoal.soccerstats.network.dto.SquadPlayerDto
import com.bakigoal.soccerstats.network.dto.SquadTeamDto

// DTO -> Entity

fun SquadDto.asEntity() =
    SquadDB(team = team.asEntity(), players = players.map { it.asEntity(team.id) })

private fun SquadTeamDto.asEntity() = SquadTeamEntity(id, name, logo)

private fun SquadPlayerDto.asEntity(teamId: Int) =
    SquadPlayerEntity(id, teamId, name, age, number, position, photo)

// Entity -> Domain

fun SquadDB.asDomain() = Squad(team = team.asDomain(), players = players.map { it.asDomain() })

private fun SquadTeamEntity.asDomain() = SquadTeam(id, name, logo)

private fun SquadPlayerEntity.asDomain() = SquadPlayer(id, name, age, number, position, photo)
