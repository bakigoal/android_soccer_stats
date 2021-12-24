package com.bakigoal.soccerstats.database.dao

import androidx.room.*
import com.bakigoal.soccerstats.database.entity.PlayerEntity
import com.bakigoal.soccerstats.database.entity.PlayerInfoDB
import com.bakigoal.soccerstats.database.entity.PlayerStatsEntity

@Dao
abstract class PlayersDao {

    @Transaction
    @Query("select * from players where leagueId=:leagueId and year=:year")
    abstract fun getTopScorers(leagueId: Int, year: String): List<PlayerInfoDB>

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