package com.bakigoal.soccerstats.network.service

import com.bakigoal.soccerstats.network.dto.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface SoccerStatsService {

    @GET("/api/v1/leagues/{leagueId}")
    fun leaguesAsync(@Path("leagueId") id: Int): Deferred<ResponseDto<LeagueDto>>

    @GET("/api/v1/leagues/{leagueId}/{season}/standings")
    fun standingsAsync(@Path("leagueId") league: Int, @Path("season") year: String): Deferred<ResponseDto<LeagueStandingsDto>>

    @GET("/api/v1/leagues/{leagueId}/{season}/topscorers")
    fun topScorersAsync(@Path("leagueId") league: Int, @Path("season") year: String): Deferred<ResponseDto<PlayerInfoDto>>

    @GET("/api/v1/leagues/{leagueId}/{season}/topassists")
    fun topAssistsAsync(@Path("leagueId") league: Int, @Path("season") year: String): Deferred<ResponseDto<PlayerInfoDto>>

    @GET("/api/v1/teams/{teamId}/squad")
    fun getSquadAsync(@Path("teamId") teamId: Int): Deferred<ResponseDto<SquadDto>>
}