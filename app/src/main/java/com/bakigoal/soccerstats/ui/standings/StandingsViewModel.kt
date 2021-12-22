package com.bakigoal.soccerstats.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bakigoal.soccerstats.domain.League
import com.bakigoal.soccerstats.domain.Season

class StandingsViewModel(private val league: League, private val currentSeasonPosition: Int) : ViewModel(){

    private var _navigateToSeason = MutableLiveData<Int?>(null)

    val seasonList: LiveData<List<Season>>
        get() = MutableLiveData(league.sortedSeasons())
    val navigateToSeason: LiveData<Int?>
        get() = _navigateToSeason

    fun seasonSelected(position: Int) {
        if (currentSeasonPosition != position) {
            _navigateToSeason.value = position
        }
    }

    fun doneNavigateToSeason() {
        _navigateToSeason.value = null
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(private val league: League, private val currentSeasonPosition: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StandingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StandingsViewModel(league, currentSeasonPosition) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}