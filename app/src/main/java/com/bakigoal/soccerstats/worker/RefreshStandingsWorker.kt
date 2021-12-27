package com.bakigoal.soccerstats.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.repository.StandingsRepository
import com.bakigoal.soccerstats.repository.TopPlayersRepository
import retrofit2.HttpException

class RefreshStandingsWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    companion object {
        const val WORK_NAME = "RefreshStandingsWorker"
    }

    override suspend fun doWork(): Result {
        val database = SoccerDatabase.getDatabase(applicationContext)
        val repository = StandingsRepository(Network.soccerStatsService, database)
        val scorersRepository = TopPlayersRepository(Network.soccerStatsService, database)

        return try {
            repository.refreshStandings()
            scorersRepository.refreshTopScorersAndAssists()
            Result.success()
        } catch (e: HttpException) {
            // retry this Job in the future
            Result.retry()
        }
    }
}
