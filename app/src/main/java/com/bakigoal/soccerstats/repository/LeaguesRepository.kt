package com.bakigoal.soccerstats.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.database.entity.LeagueDB
import com.bakigoal.soccerstats.domain.League
import com.bakigoal.soccerstats.domain.LeaguesEnum
import com.bakigoal.soccerstats.mappers.asDomain
import com.bakigoal.soccerstats.mappers.asEntity
import com.bakigoal.soccerstats.network.dto.LeagueDto
import com.bakigoal.soccerstats.network.service.SoccerStatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching countries from the network and storing them on disk
 */
class LeaguesRepository(
    private val soccerStatsService: SoccerStatsService,
    private val database: SoccerDatabase
) {

    val leagues: LiveData<List<League>> =
        Transformations.map(database.leaguesDao.getAll()) { list ->
            list.map(LeagueDB::asDomain).sortedWith(compareBy { LeaguesEnum.fromId(it.id).order })
        }

    suspend fun isLeaguesDbEmpty(): Boolean = withContext(Dispatchers.IO) {
        database.leaguesDao.getCount() == 0
    }

    suspend fun refreshLeagues() = withContext(Dispatchers.IO) {
        LeaguesEnum.values().forEach { refreshLeague(it.id) }
    }

    private suspend fun refreshLeague(leagueId: Int) = withContext(Dispatchers.IO) {
        val responseDto = soccerStatsService.leaguesAsync(leagueId).await()
        val response: List<LeagueDto> = responseDto.response
        database.leaguesDao.insertAll(*response.asEntity())
    }
}