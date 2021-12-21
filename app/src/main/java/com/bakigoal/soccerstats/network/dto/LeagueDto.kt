package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueDto(
    val league: LeagueInfoDto,
    val country: CountryDto,
    val seasons: List<SeasonDto>
)

data class LeagueInfoDto(
    val id: Int,
    val name: String,
    val type: String,
    val logo: String
)

data class CountryDto(
    val name: String,
    val code: String?,
    val flag: String?
)

data class SeasonDto(
    val year: String,
    val start: String,
    val end: String,
    val current: Boolean,
)
