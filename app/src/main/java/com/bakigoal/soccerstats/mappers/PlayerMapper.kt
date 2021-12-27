package com.bakigoal.soccerstats.mappers

import com.bakigoal.soccerstats.database.entity.BirthEntity
import com.bakigoal.soccerstats.database.entity.PlayerEntity
import com.bakigoal.soccerstats.database.entity.PlayerInfoDB
import com.bakigoal.soccerstats.database.entity.PlayerStatsEntity
import com.bakigoal.soccerstats.domain.Birth
import com.bakigoal.soccerstats.domain.Player
import com.bakigoal.soccerstats.domain.PlayerInfo
import com.bakigoal.soccerstats.domain.PlayerStats
import com.bakigoal.soccerstats.network.dto.*

fun List<PlayerInfoDto>.asEntity(type:String) = map { it.asEntity(type) }.toTypedArray()

fun PlayerInfoDto.asEntity(type: String) = PlayerInfoDB(
    player = player.asEntity(statistics[0].league, type),
    statistics = statistics[0].asEntity(player.id, type)
)

private fun PlayerStatsDto.asEntity(playerId: Int, type: String) = PlayerStatsEntity(
    id = "${league.id}_${league.season}_${playerId}_$type",
    year = league.season,
    leagueId = league.id,
    teamId = team.id,
    teamName = team.name,
    teamLogo = team.logo,
    total = goals.total ?: 0,
    assists = goals.assists ?: 0,
    saves = goals.saves ?: 0
)

private fun PlayerDto.asEntity(league: PlayerLeagueDto, type: String) = PlayerEntity(
    id = "${league.id}_${league.season}_${id}_$type",
    playerId = id,
    year = league.season,
    leagueId = league.id,
    type = type,
    name = name,
    firstname = firstname,
    lastname = lastname,
    age = age,
    birth = birth.asEntity(),
    nationality, height, weight, injured, photo
)

private fun BirthDto.asEntity() = BirthEntity(date, place, country)

// entity -> domain

fun PlayerInfoDB.asDomain() = PlayerInfo(
    player = player.asDomain(),
    playerStats = statistics.asDomain()
)

private fun PlayerEntity.asDomain() = Player(
    id = id,
    playerId = playerId,
    year = year,
    leagueId = leagueId,
    name = name,
    firstname = firstname,
    lastname = lastname,
    age = age,
    birth = birth.asDomain(),
    nationality, height, weight, injured, photo
)

private fun BirthEntity.asDomain() = Birth(date, place, country)

private fun PlayerStatsEntity.asDomain() = PlayerStats(
    id, year, leagueId, teamId, teamName, teamLogo, total, assists, saves
)
