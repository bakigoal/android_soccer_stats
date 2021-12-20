package com.bakigoal.soccerstats.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bakigoal.soccerstats.R

/**
 * This is a single activity application that uses the Navigation library. Content is displayed
 * by Fragments.
 */
class SoccerStatsActivity : AppCompatActivity() {

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_viewer)
    }
}
