package com.bakigoal.soccerstats.mappers

import com.bakigoal.soccerstats.database.entity.StandingTeamEntity
import com.bakigoal.soccerstats.database.entity.StandingsDB
import com.bakigoal.soccerstats.database.entity.StandingsLeagueEntity
import com.bakigoal.soccerstats.database.entity.StatsEmbedded
import com.bakigoal.soccerstats.domain.StandingTeam
import com.bakigoal.soccerstats.domain.Standings
import com.bakigoal.soccerstats.domain.Stats
import com.bakigoal.soccerstats.network.dto.LeagueStandingsDto
import com.bakigoal.soccerstats.network.dto.StandingDto
import com.bakigoal.soccerstats.network.dto.StandingStatsDto
import com.bakigoal.soccerstats.network.dto.StandingsDto

const val LEAGUE_SEASON_SEPARATOR = "___"

fun glueLeagueIdAndSeason(leagueId:Int, season: String)  = "${leagueId}$LEAGUE_SEASON_SEPARATOR${season}"

// dto -> entity

fun LeagueStandingsDto.asEntity(): StandingsDB = StandingsDB(
    leagueInfo = league.asEntity(),
    standings = league.standings[0].map { it.asEntity(glueLeagueIdAndSeason(league.id,league.season)) }
)

private fun StandingsDto.asEntity() = StandingsLeagueEntity(
    leagueSeasonId = glueLeagueIdAndSeason(id, season),
    leagueName = name,
    leagueLogo = logo,
    countryFlag = flag
)

private fun StandingDto.asEntity(leagueSeasonId: String) = StandingTeamEntity(
    leagueSeasonId = leagueSeasonId,
    rank = rank,
    points = points,
    form = form,
    teamId = team.id,
    teamName = team.name,
    teamLogo = team.logo,
    description = description,
    all = all.asEntity(),
    home = home.asEntity(),
    away = away.asEntity()
)

private fun StandingStatsDto.asEntity() = StatsEmbedded(
    played = played,
    win = win,
    draw = draw,
    lose = lose,
    goalsFor = goals.goalsFor,
    goalsAgainst = goals.goalsAgainst
)

// entity -> domain

fun StandingsDB.asDomain() = Standings(
    leagueId = leagueInfo.leagueSeasonId.split(LEAGUE_SEASON_SEPARATOR)[0],
    season = leagueInfo.leagueSeasonId.split(LEAGUE_SEASON_SEPARATOR)[1],
    standings = standings.map { it.asDomain() }
)

private fun StandingTeamEntity.asDomain() = StandingTeam(
    rank = rank,
    points = points,
    form = form,
    teamId = teamId,
    teamName = teamName,
    teamLogo = teamLogo,
    description = description,
    all = all.asDomain(),
    home = home.asDomain(),
    away = away.asDomain()
)

private fun StatsEmbedded.asDomain() = Stats(played, win, draw, lose, goalsFor, goalsAgainst)
