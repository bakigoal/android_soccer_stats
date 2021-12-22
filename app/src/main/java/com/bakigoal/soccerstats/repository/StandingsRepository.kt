package com.bakigoal.soccerstats.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

    fun getStandings(leagueId: Int, year: String): LiveData<Standings> {
        val leagueIdAndSeason = glueLeagueIdAndSeason(leagueId, year)
        return Transformations.map(database.standingsDao.findById(leagueIdAndSeason)) {
            it?.asDomain()
        }
    }

    suspend fun hasStandings(leagueId: Int, year: String): Boolean = withContext(Dispatchers.IO) {
        database.standingsDao.getCount(glueLeagueIdAndSeason(leagueId, year)) != 0
    }

    suspend fun refreshStanding(leagueId: Int, year: String) = withContext(Dispatchers.IO) {
        val dto = soccerStatsService.standingsAsync(leagueId, year).await()
        val response: List<LeagueStandingsDto> = dto.response
        val entity = response[0].asEntity()
        database.standingsDao.insert(entity)
    }
}