package com.bakigoal.soccerstats.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.Standings
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.repository.StandingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TableViewModel(
    application: Application,
    private val leagueId: Int,
    private val year: String
) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = SoccerDatabase.getDatabase(application)
    private val standingsRepository = StandingsRepository(Network.soccerStatsService, database)

    private val _standings = MutableLiveData<Standings?>(null)

    val standings: LiveData<Standings?>
        get() = _standings

    init {
        viewModelScope.launch {
            _standings.value = standingsRepository.getStandings(leagueId, year)
            Log.i(javaClass.simpleName, "init... ${_standings.value}")
            if(_standings.value == null) {
                standingsRepository.refreshStanding(leagueId, year)
                _standings.value = standingsRepository.getStandings(leagueId, year)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(
        private val application: Application,
        private val leagueId: Int,
        private val year: String
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TableViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TableViewModel(application, leagueId, year) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}