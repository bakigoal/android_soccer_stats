package com.bakigoal.soccerstats.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bakigoal.soccerstats.database.CountriesDatabase
import com.bakigoal.soccerstats.repository.CountriesRepository
import retrofit2.HttpException

class RefreshDataWork(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = CountriesDatabase.getDatabase(applicationContext)
        val repository = CountriesRepository(database)

        return try {
            repository.refreshCountries()
            Result.success()
        } catch (e: HttpException) {
            // retry this Job in the future
            Result.retry()
        }
    }
}
