package com.bakigoal.soccerstats.ui.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.domain.Squad
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.repository.SquadsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TeamViewModel(app: Application, private val teamId: Int) : AndroidViewModel(app) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val db = SoccerDatabase.getDatabase(app)
    private val squadsRepository = SquadsRepository(Network.soccerStatsService, db)

    private val _squad = MutableLiveData<Squad>(null)
    val squad: LiveData<Squad>
        get() = _squad

    init {
        viewModelScope.launch {
            _squad.value = squadsRepository.getSquads(teamId)
            if (_squad.value == null) {
                squadsRepository.refreshSquad(teamId)
                _squad.value = squadsRepository.getSquads(teamId)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(private val app: Application, private val teamId: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TeamViewModel(app, teamId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}