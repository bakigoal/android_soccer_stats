package com.bakigoal.soccerstats.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
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

    private val _show_error = MutableLiveData("")

    val countries: LiveData<List<Country>> = countriesRepository.countries
    val showError: LiveData<String>
        get() = _show_error

    init {
        viewModelScope.launch {
            try {
                countriesRepository.refreshCountries()
            } catch (error: Throwable) {
                _show_error.value = "No internet..."
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneShowError () {
        _show_error.value = ""
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SoccerStatsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SoccerStatsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
