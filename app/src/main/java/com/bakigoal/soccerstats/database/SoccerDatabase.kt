package com.bakigoal.soccerstats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bakigoal.soccerstats.database.dao.LeaguesDao
import com.bakigoal.soccerstats.database.dao.StandingsDao
import com.bakigoal.soccerstats.database.entity.*

@Database(
    entities = [
        LeagueInfoEntity::class,
        CountryEntity::class,
        SeasonEntity::class,
        StandingTeamEntity::class,
        StandingsLeagueEntity::class
    ],
    version = 9
)
abstract class SoccerDatabase : RoomDatabase() {
    abstract val leaguesDao: LeaguesDao
    abstract val standingsDao: StandingsDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: SoccerDatabase

        fun getDatabase(context: Context): SoccerDatabase {
            if (::INSTANCE.isInitialized) {
                return INSTANCE
            }
            initDb(context)
            return INSTANCE
        }

        private fun initDb(context: Context) = synchronized(this) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, SoccerDatabase::class.java, "soccer-stats"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}