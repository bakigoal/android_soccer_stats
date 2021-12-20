package com.bakigoal.soccerstats.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.database.entity.LeagueEntity
import com.bakigoal.soccerstats.database.entity.toDomainModel
import com.bakigoal.soccerstats.domain.League
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.network.dto.LeagueDto
import com.bakigoal.soccerstats.network.dto.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching countries from the network and storing them on disk
 */
class LeaguesRepository(private val database: SoccerDatabase) {

    companion object {
        private const val RPL_ID = 237
        private const val PREMIER_LEAGUE_ID = 39
        private const val LA_LIGA_ID = 140
        private const val BUNDESLIGA_ID = 78
        private const val SERIE_A_ID = 135
        private const val LEAGUE_1_ID = 61

        val leagueList =
            listOf(RPL_ID, PREMIER_LEAGUE_ID, LA_LIGA_ID, BUNDESLIGA_ID, SERIE_A_ID, LEAGUE_1_ID)
    }

    val leagues: LiveData<List<League>> =
        Transformations.map(database.leaguesDao.getAll()) { it.map(LeagueEntity::toDomainModel) }

    suspend fun refreshLeagues() = withContext(Dispatchers.IO) {
        leagueList.forEach { leagueId -> refreshLeague(leagueId) }
    }

    private suspend fun refreshLeague(leagueId: Int) = withContext(Dispatchers.IO) {
        val responseDto = Network.soccerStatsService.leaguesAsync(leagueId).await()
        val response: List<LeagueDto> = responseDto.response
        database.leaguesDao.insertAll(*response.toEntity())
    }
}