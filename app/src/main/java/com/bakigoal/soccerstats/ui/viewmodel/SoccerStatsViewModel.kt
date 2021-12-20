package com.bakigoal.soccerstats.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bakigoal.soccerstats.database.CountriesDatabase.Companion.getDatabase
import com.bakigoal.soccerstats.domain.Country
import com.bakigoal.soccerstats.repository.CountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SoccerStatsViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val countriesRepository = CountriesRepository(database)

    val countries: LiveData<List<Country>> = countriesRepository.countries

    init {
        viewModelScope.launch {
            countriesRepository.refreshCountries()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SoccerStatsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SoccerStatsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
