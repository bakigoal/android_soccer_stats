package com.bakigoal.soccerstats.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bakigoal.soccerstats.database.CountriesDatabase
import com.bakigoal.soccerstats.database.entity.toDomainModel
import com.bakigoal.soccerstats.domain.Country
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.network.dto.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching countries from the network and storing them on disk
 */
class CountriesRepository(private val database: CountriesDatabase) {

    val countries: LiveData<List<Country>> =
        Transformations.map(database.countriesDao.getAll()) { it.toDomainModel() }

    suspend fun refreshCountries() = withContext(Dispatchers.IO) {
        val responseDto = Network.soccerStatsService.countriesAsync().await()
        database.countriesDao.insertAll(*responseDto.response.toEntity())
    }
}