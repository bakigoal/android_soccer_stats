package com.bakigoal.soccerstats.ui.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.PlayerInfo
import com.bakigoal.soccerstats.domain.PlayerStatType
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.repository.TopPlayersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TopPlayersViewModel(
    application: Application,
    private val leagueId: Int,
    private val year: String,
    private val type: PlayerStatType
) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = SoccerDatabase.getDatabase(application)
    private val topPlayersRepository = TopPlayersRepository(Network.soccerStatsService, database)

    private val _players = MutableLiveData<List<PlayerInfo>>(null)

    val players: LiveData<List<PlayerInfo>>
        get() = _players

    init {
        viewModelScope.launch {
            _players.value = topPlayersRepository.getTopPlayers(leagueId, year, type)
            if (_players.value == null || _players.value!!.isEmpty()) {
                topPlayersRepository.refreshTopPlayers(leagueId, year, type)
                _players.value = topPlayersRepository.getTopPlayers(leagueId, year, type)
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
        private val year: String,
        private val type: PlayerStatType
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TopPlayersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TopPlayersViewModel(application, leagueId, year, type) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}