package com.bakigoal.soccerstats.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bakigoal.soccerstats.database.entity.CountryEntity

@Dao
interface CountriesDao {

    @Query("select * from db_country")
    fun getAll(): LiveData<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg countries: CountryEntity)
}