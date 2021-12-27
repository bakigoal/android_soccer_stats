package com.bakigoal.soccerstats.database.dao

import androidx.room.*
import com.bakigoal.soccerstats.database.entity.PlayerEntity
import com.bakigoal.soccerstats.database.entity.PlayerInfoDB
import com.bakigoal.soccerstats.database.entity.PlayerStatsEntity

@Dao
abstract class PlayersDao {

    @Transaction
    @Query("select * from players where leagueId=:leagueId and year=:year and type='GOAL'")
    abstract fun getTopScorers(leagueId: Int, year: String): List<PlayerInfoDB>

    @Transaction
    @Query("select * from players where leagueId=:leagueId and year=:year and type=:type")
    abstract fun getPlayersStatistics(leagueId: Int, year: String, type: String): List<PlayerInfoDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(playerEntity: PlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(playerStatsEntity: PlayerStatsEntity)

    @Transaction
    open fun insertAll(vararg playerInfoDBs: PlayerInfoDB) {
        playerInfoDBs.forEach {
            insert(it.player)
            insert(it.statistics)
        }
    }
}