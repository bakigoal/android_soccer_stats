package com.bakigoal.soccerstats.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.bakigoal.soccerstats.database.entity.CountryEntity
import com.bakigoal.soccerstats.database.entity.LeagueWithCountryAndSeasons
import com.bakigoal.soccerstats.database.entity.LeagueEntity
import com.bakigoal.soccerstats.database.entity.SeasonEntity

@Dao
abstract class LeaguesDao {

    @Transaction
    @Query("select * from leagues")
    abstract fun getAll(): LiveData<List<LeagueWithCountryAndSeasons>>

    @Insert(onConflict = REPLACE)
    abstract fun insert(leagueEntity: LeagueEntity)

    @Insert(onConflict = REPLACE)
    abstract fun insert(countryEntity: CountryEntity)

    @Insert(onConflict = REPLACE)
    abstract fun insert(seasonEntity: SeasonEntity)

    @Transaction
    open fun insertAll(vararg leagues: LeagueWithCountryAndSeasons){
        leagues.forEach { league ->
            insert(league.league)
            insert(league.country)
            league.seasons.forEach(this::insert)
        }
    }
}