package com.bakigoal.soccerstats.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.repository.LeaguesRepository
import retrofit2.HttpException

class RefreshDataWork(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = SoccerDatabase.getDatabase(applicationContext)
        val repository = LeaguesRepository(database)

        return try {
            repository.refreshLeagues()
            Result.success()
        } catch (e: HttpException) {
            // retry this Job in the future
            Result.retry()
        }
    }
}
