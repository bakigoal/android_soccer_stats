package com.bakigoal.soccerstats.network.service

import com.bakigoal.soccerstats.network.dto.CountryDto
import com.bakigoal.soccerstats.network.dto.ResponseDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface SoccerStatsService {

    @GET("countries")
    fun countriesAsync(): Deferred<ResponseDto<CountryDto>>
}