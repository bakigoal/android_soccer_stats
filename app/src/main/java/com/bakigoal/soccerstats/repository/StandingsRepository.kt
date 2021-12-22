package com.bakigoal.soccerstats.repository

import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.Standings
import com.bakigoal.soccerstats.mappers.asDomain
import com.bakigoal.soccerstats.mappers.asEntity
import com.bakigoal.soccerstats.mappers.glueLeagueIdAndSeason
import com.bakigoal.soccerstats.network.dto.LeagueStandingsDto
import com.bakigoal.soccerstats.network.service.SoccerStatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StandingsRepository(
    private val soccerStatsService: SoccerStatsService,
    private val database: SoccerDatabase
) {

    suspend fun getStandings(leagueId: Int, year: String): Standings? =
        withContext(Dispatchers.IO) {
            val leagueIdAndSeason = glueLeagueIdAndSeason(leagueId, year)
            database.standingsDao.findById(leagueIdAndSeason)?.asDomain()
        }

    suspend fun refreshStanding(leagueId: Int, year: String) = withContext(Dispatchers.IO) {
        val dto = soccerStatsService.standingsAsync(leagueId, year).await()
        val response: List<LeagueStandingsDto> = dto.response
        val entity = response[0].asEntity()
        database.standingsDao.insert(entity)
    }
}