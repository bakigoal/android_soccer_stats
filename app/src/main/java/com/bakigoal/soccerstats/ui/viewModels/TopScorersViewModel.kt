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

class TopScorersViewModel(
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
            _players.value = topScorersRepository.getTopScorers(leagueId, year)
            if (_players.value == null || _players.value!!.isEmpty()) {
                topScorersRepository.refreshTopScorers(leagueId, year)
                _players.value = topScorersRepository.getTopScorers(leagueId, year)
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
            if (modelClass.isAssignableFrom(TopScorersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TopScorersViewModel(application, leagueId, year) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}