package com.bakigoal.soccerstats.mappers

import com.bakigoal.soccerstats.database.entity.CountryEntity
import com.bakigoal.soccerstats.database.entity.LeagueInfoEntity
import com.bakigoal.soccerstats.database.entity.LeagueDB
import com.bakigoal.soccerstats.database.entity.SeasonEntity
import com.bakigoal.soccerstats.domain.Country
import com.bakigoal.soccerstats.domain.League
import com.bakigoal.soccerstats.domain.Season
import com.bakigoal.soccerstats.network.dto.CountryDto
import com.bakigoal.soccerstats.network.dto.LeagueDto
import com.bakigoal.soccerstats.network.dto.LeagueInfoDto
import com.bakigoal.soccerstats.network.dto.SeasonDto

fun LeagueDto.asEntity(): LeagueDB = LeagueDB(
    league = this.league.asEntity(),
    country = this.country.asEntity(this.league.id),
    seasons = this.seasons.asEntity(this.league.id)
)

fun List<LeagueDto>.asEntity(): Array<LeagueDB> =
    map { it.asEntity() }.toTypedArray()

fun LeagueDB.asDomain(): League = League(
    id = this.league.id,
    name = this.league.name,
    type = this.league.type,
    logo = this.league.logo,
    country = this.country.asDomain(),
    seasons = this.seasons.asDomain()
)

// --- To Entity ---

private fun List<SeasonDto>.asEntity(leagueId: Int): List<SeasonEntity> =
    map { it.asEntity(leagueId) }

private fun SeasonDto.asEntity(leagueId: Int): SeasonEntity =
    SeasonEntity(year = year, leagueId = leagueId, start = start, end = end, current = current)

private fun CountryDto.asEntity(leagueId: Int): CountryEntity =
    CountryEntity(name = name, code = code, flag = flag, leagueId = leagueId)

private fun LeagueInfoDto.asEntity(): LeagueInfoEntity = LeagueInfoEntity(id, name, type, logo)

// --- To Domain ---

private fun List<SeasonEntity>.asDomain(): List<Season> = map { it.asDomain() }

private fun SeasonEntity.asDomain(): Season = Season(year, start, end, current)

private fun CountryEntity.asDomain(): Country = Country(name, code, flag)