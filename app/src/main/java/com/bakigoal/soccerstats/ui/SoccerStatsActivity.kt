package com.bakigoal.soccerstats.ui

import android.os.Bundle
import android.view.Menu.NONE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.database.SoccerDatabase
import com.bakigoal.soccerstats.databinding.ActivityLeagueViewerBinding
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.repository.LeaguesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * This is a single activity application that uses the Navigation library. Content is displayed
 * by Fragments.
 */
class SoccerStatsActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityLeagueViewerBinding

    private val activityJob = SupervisorJob()
    private val activityScope = CoroutineScope(activityJob + Dispatchers.Main)

    private lateinit var leaguesRepository: LeaguesRepository

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_league_viewer)
        drawerLayout = binding.drawerLayout
        val navHost =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id == controller.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navView, navController)

        leaguesRepository =
            LeaguesRepository(Network.soccerStatsService, SoccerDatabase.getDatabase(application))
        setupDrawerMenuItems()
    }

    private fun setupDrawerMenuItems() = activityScope.launch {
        leaguesRepository.leagues.observe(this@SoccerStatsActivity, {
            val menu = binding.navView.menu
            menu.clear()
            it.forEachIndexed { index, league ->
                menu.add(NONE, league.id, index, league.name)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onDestroy() {
        super.onDestroy()
        activityJob.cancel()
    }
}
