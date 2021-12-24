package com.bakigoal.soccerstats.repository

import coil.map.Mapper
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

    suspend fun getTopScorers(leagueId: Int, year: String): List<PlayerInfo> =
        withContext(Dispatchers.IO) {
            val players = database.playersDao.getTopScorers(leagueId, year).map { it.asDomain() }
            populatePositions(players, goalsMapper())
        }

    suspend fun getTopAssists(leagueId: Int, year: String): List<PlayerInfo> =
        withContext(Dispatchers.IO) {
            val dto = soccerStatsService.topAssistsAsync(leagueId, year).await()
            val response: List<PlayerInfoDto> = dto.response
            populatePositions(response.asDomain(), assistsMapper())
        }

    private fun assistsMapper() = object : Mapper<PlayerInfo, Int> {
        override fun map(data: PlayerInfo) = data.playerStats.assists
    }

    private fun goalsMapper() = object : Mapper<PlayerInfo, Int> {
        override fun map(data: PlayerInfo) = data.playerStats.total
    }

    private fun populatePositions(
        players: List<PlayerInfo>,
        mapper: Mapper<PlayerInfo, Int>
    ): List<PlayerInfo> {
        val positions = mutableMapOf<Int, Int>()
        players.map { mapper.map(it) }.toSet().forEachIndexed { index, goals ->
            positions[goals] = index + 1
        }
        return players.map {
            it.scorerPosition = positions[mapper.map(it)]!!
            it
        }
    }
}
