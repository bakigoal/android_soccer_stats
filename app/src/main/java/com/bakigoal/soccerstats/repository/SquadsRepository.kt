package com.bakigoal.soccerstats.repository

import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.Squad
import com.bakigoal.soccerstats.mappers.asDomain
import com.bakigoal.soccerstats.mappers.asEntity
import com.bakigoal.soccerstats.network.service.SoccerStatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SquadsRepository(
    private val soccerStatsService: SoccerStatsService,
    private val database: SoccerDatabase
) {

    suspend fun getSquads(teamId: Int): Squad? = withContext(Dispatchers.IO) {
        database.squadsDao.getTeamSquad(teamId)?.asDomain()
    }

    suspend fun refreshSquad(teamId: Int) = withContext(Dispatchers.IO) {
        val response = soccerStatsService.getSquad(teamId).await().response
        if (response.isNotEmpty()) {
            database.squadsDao.insertAll(response[0].asEntity())
        }
    }

    suspend fun refresSquads() = withContext(Dispatchers.IO) {
        database.squadsDao.getIds().forEach { id ->
            refreshSquad(id)
        }
    }
}