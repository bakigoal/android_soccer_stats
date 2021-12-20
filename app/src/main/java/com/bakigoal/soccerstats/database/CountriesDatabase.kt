package com.bakigoal.soccerstats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bakigoal.soccerstats.database.dao.CountriesDao
import com.bakigoal.soccerstats.database.entity.CountryEntity

@Database(entities = [CountryEntity::class], version = 2)
abstract class CountriesDatabase : RoomDatabase() {
    abstract val countriesDao: CountriesDao

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