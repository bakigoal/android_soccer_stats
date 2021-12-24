package com.bakigoal.soccerstats.ui.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.PlayerInfo
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.repository.TopScorersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TopAssistsViewModel(
    application: Application,
    private val leagueId: Int,
    private val year: String
) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = SoccerDatabase.getDatabase(application)
    private val topScorersRepository = TopScorersRepository(Network.soccerStatsService, database)

    private val _players = MutableLiveData<List<PlayerInfo>>(null)

    val players: LiveData<List<PlayerInfo>>
        get() = _players

    init {
        viewModelScope.launch {
            _players.value = topScorersRepository.getTopAssists(leagueId, year)
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
            if (modelClass.isAssignableFrom(TopAssistsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TopAssistsViewModel(application, leagueId, year) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}