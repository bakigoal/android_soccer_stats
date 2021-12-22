package com.bakigoal.soccerstats

import android.app.Application
import android.os.Build
import androidx.work.*
import com.bakigoal.soccerstats.worker.RefreshLeaguesWorker
import com.bakigoal.soccerstats.worker.RefreshStandingsWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Override application to setup background work via WorkManager
 */
class SoccerStatsApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRefreshDataWorker()
            setupRefreshStandingsWorker()
        }
    }

    private fun setupRefreshDataWorker() {
        val leagueWorkRequest = PeriodicWorkRequestBuilder<RefreshLeaguesWorker>(1, TimeUnit.DAYS)
            .setConstraints(workerConstraints())
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshLeaguesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            leagueWorkRequest
        )
    }

    private fun setupRefreshStandingsWorker() {
        val standingsWorkRequest = PeriodicWorkRequestBuilder<RefreshStandingsWorker>(12, TimeUnit.HOURS)
            .setConstraints(workerConstraints())
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshStandingsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            standingsWorkRequest
        )
    }

    private fun workerConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(true)
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }
        .build()
}
