package com.bakigoal.soccerstats.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bakigoal.soccerstats.database.entity.LeagueEntity

@Dao
interface LeaguesDao {

    @Query("select * from db_league")
    fun getAll(): LiveData<List<LeagueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg leagues: LeagueEntity)
}