package com.bakigoal.soccerstats.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.bakigoal.soccerstats.database.entity.CountryEntity
import com.bakigoal.soccerstats.database.entity.LeagueDB
import com.bakigoal.soccerstats.database.entity.LeagueInfoEntity
import com.bakigoal.soccerstats.database.entity.SeasonEntity

@Dao
abstract class LeaguesDao {

    @Transaction
    @Query("select * from leagues")
    abstract fun getAll(): LiveData<List<LeagueDB>>

    @Query("select count(*) from leagues")
    abstract fun getCount(): Int

    @Insert(onConflict = REPLACE)
    abstract fun insert(leagueEntity: LeagueInfoEntity)

    @Insert(onConflict = REPLACE)
    abstract fun insert(countryEntity: CountryEntity)

    @Insert(onConflict = REPLACE)
    abstract fun insert(seasonEntity: SeasonEntity)

    @Transaction
    open fun insertAll(vararg leagues: LeagueDB){
        leagues.forEach { league ->
            insert(league.league)
            insert(league.country)
            league.seasons.forEach(this::insert)
        }
    }
}