package com.bakigoal.soccerstats.network.dto

import com.bakigoal.soccerstats.database.entity.CountryEntity
import com.bakigoal.soccerstats.database.entity.LeagueEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueDto(
    val league: LeagueInfoDto,
    val country: CountryDto
)

data class LeagueInfoDto(
    val id: String,
    val name: String,
    val type: String,
    val logo: String
)

data class CountryDto(
    val name: String,
    val code: String?,
    val flag: String?
)

/**
 * Convert Dto to database objects
 */

fun LeagueDto.toEntity(): LeagueEntity = LeagueEntity(
    id = this.league.id,
    name = this.league.name,
    type = this.league.type,
    logo = this.league.logo,
    country = this.country.toEntity()
)

fun CountryDto.toEntity(): CountryEntity = CountryEntity(countryCode = this.code, countryName = this.name, countryFlag = this.flag)

fun List<LeagueDto>.toEntity(): Array<LeagueEntity> = map(LeagueDto::toEntity).toTypedArray()
