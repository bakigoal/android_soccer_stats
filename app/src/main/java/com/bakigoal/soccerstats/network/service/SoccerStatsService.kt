package com.bakigoal.soccerstats.network.service

import com.bakigoal.soccerstats.network.dto.CountryDto
import com.bakigoal.soccerstats.network.dto.LeagueDto
import com.bakigoal.soccerstats.network.dto.ResponseDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface SoccerStatsService {

    @GET("countries")
    fun countriesAsync(): Deferred<ResponseDto<CountryDto>>

    @GET("leagues")
    fun leaguesAsync(@Query("id") id: Int): Deferred<ResponseDto<LeagueDto>>
}