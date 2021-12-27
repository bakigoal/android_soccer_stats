package com.bakigoal.soccerstats.network.service

import com.bakigoal.soccerstats.network.dto.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface SoccerStatsService {

    @GET("leagues")
    fun leaguesAsync(@Query("id") id: Int): Deferred<ResponseDto<LeagueDto>>

    @GET("standings")
    fun standingsAsync(@Query("league") league: Int, @Query("season") year: String): Deferred<ResponseDto<LeagueStandingsDto>>

    @GET("players/topscorers")
    fun topScorersAsync(@Query("league") league: Int, @Query("season") year: String): Deferred<ResponseDto<PlayerInfoDto>>

    @GET("players/topassists")
    fun topAssistsAsync(@Query("league") league: Int, @Query("season") year: String): Deferred<ResponseDto<PlayerInfoDto>>

    @GET("players/squads")
    fun getSquad(@Query("team") teamId: Int): Deferred<ResponseDto<SquadDto>>
}