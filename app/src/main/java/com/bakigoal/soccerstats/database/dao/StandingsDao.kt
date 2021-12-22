package com.bakigoal.soccerstats.database.dao

import androidx.room.*
import com.bakigoal.soccerstats.database.entity.StandingTeamEntity
import com.bakigoal.soccerstats.database.entity.StandingsDB
import com.bakigoal.soccerstats.database.entity.StandingsLeagueEntity

@Dao
abstract class StandingsDao {


    @Transaction
    @Query("select * from standings where leagueSeasonId=:leagueIdAndSeason limit 1")
    abstract fun findById(leagueIdAndSeason: String): StandingsDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(standingTeamEntity: StandingTeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(standingsLeagueEntity: StandingsLeagueEntity)

    @Transaction
    open fun insert(standingsDB: StandingsDB) {
        insert(standingsDB.leagueInfo)
        standingsDB.standings.forEach {
            insert(it)
        }
    }


}