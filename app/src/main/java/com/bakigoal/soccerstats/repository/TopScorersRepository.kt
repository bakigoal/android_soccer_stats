package com.bakigoal.soccerstats.repository

import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.PlayerInfo
import com.bakigoal.soccerstats.mappers.asDomain
import com.bakigoal.soccerstats.mappers.asEntity
import com.bakigoal.soccerstats.mappers.parseGluedId
import com.bakigoal.soccerstats.network.dto.PlayerInfoDto
import com.bakigoal.soccerstats.network.service.SoccerStatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopScorersRepository(
    private val soccerStatsService: SoccerStatsService,
    private val database: SoccerDatabase
) {

    suspend fun getTopScorers(leagueId: Int, year: String): List<PlayerInfo> =
        withContext(Dispatchers.IO) {
            populatePositions(database.playersDao.getTopScorers(leagueId, year).map { it.asDomain() })
        }

    private fun populatePositions(players: List<PlayerInfo>):List<PlayerInfo> {
        val positions = mutableMapOf<Int, Int>()
        players.map { it.playerStats.total }.toSet().forEachIndexed { index, goals ->
            positions[goals] = index + 1
        }
        return players.map {
            it.scorerPosition =  positions[it.playerStats.total]!!
            it
        }
    }

    suspend fun refreshTopScorers(leagueId: Int, year: String) = withContext(Dispatchers.IO) {
        val dto = soccerStatsService.topScorersAsync(leagueId, year).await()
        val response: List<PlayerInfoDto> = dto.response
        database.playersDao.insertAll(*response.map { it.asEntity() }.toTypedArray())
    }

    suspend fun refreshTopScorers() = withContext(Dispatchers.IO) {
        database.standingsDao.findAllIds().map { parseGluedId(it) }.forEach {
            refreshTopScorers(it.first, it.second)
        }
    }
}