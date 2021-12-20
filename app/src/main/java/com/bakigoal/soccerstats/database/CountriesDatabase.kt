package com.bakigoal.soccerstats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bakigoal.soccerstats.database.dao.LeaguesDao
import com.bakigoal.soccerstats.database.entity.LeagueEntity

@Database(entities = [LeagueEntity::class], version = 4)
abstract class CountriesDatabase : RoomDatabase() {
    abstract val leaguesDao: LeaguesDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: CountriesDatabase

        fun getDatabase(context: Context): CountriesDatabase {
            if (::INSTANCE.isInitialized) {
                return INSTANCE
            }
            initDb(context)
            return INSTANCE
        }

        private fun initDb(context: Context) = synchronized(this) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, CountriesDatabase::class.java, "countries"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}