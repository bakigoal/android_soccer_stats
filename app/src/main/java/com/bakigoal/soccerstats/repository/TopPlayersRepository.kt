package com.bakigoal.soccerstats.repository

import coil.map.Mapper
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.PlayerInfo
import com.bakigoal.soccerstats.domain.PlayerStatType
import com.bakigoal.soccerstats.mappers.asDomain
import com.bakigoal.soccerstats.mappers.asEntity
import com.bakigoal.soccerstats.mappers.parseGluedId
import com.bakigoal.soccerstats.network.service.SoccerStatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopPlayersRepository(
    private val soccerStatsService: SoccerStatsService,
    private val database: SoccerDatabase
) {
    suspend fun refreshTopPlayers(leagueId: Int, year: String, type: PlayerStatType) =
        withContext(Dispatchers.IO) {
            val response = when (type) {
                PlayerStatType.GOAL -> soccerStatsService.topScorersAsync(leagueId, year)
                    .await().response
                PlayerStatType.ASSIST -> soccerStatsService.topAssistsAsync(leagueId, year)
                    .await().response
            }
            database.playersDao.insertAll(*response.asEntity(type.name))
        }

    suspend fun refreshTopScorersAndAssists() = withContext(Dispatchers.IO) {
        database.standingsDao.findAllIds().map { parseGluedId(it) }.forEach {
            refreshTopPlayers(it.first, it.second, PlayerStatType.GOAL)
            refreshTopPlayers(it.first, it.second, PlayerStatType.ASSIST)
        }
    }

    suspend fun getTopPlayers(leagueId: Int, year: String, type: PlayerStatType): List<PlayerInfo> =
        withContext(Dispatchers.IO) {
            val players = database.playersDao.getPlayersStatistics(leagueId, year, type.name)
                .map { it.asDomain() }
            populatePositions(players, mapper(type))
        }

    private fun mapper(type: PlayerStatType): Mapper<PlayerInfo, Int> {
        return when (type) {
            PlayerStatType.GOAL -> object : Mapper<PlayerInfo, Int> {
                override fun map(data: PlayerInfo) = data.playerStats.total
            }
            PlayerStatType.ASSIST -> object : Mapper<PlayerInfo, Int> {
                override fun map(data: PlayerInfo) = data.playerStats.assists
            }
        }
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
