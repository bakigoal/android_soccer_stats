package com.bakigoal.soccerstats.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bakigoal.soccerstats.ui.fragments.TableFragment
import com.bakigoal.soccerstats.ui.fragments.TopAssistsFragment
import com.bakigoal.soccerstats.ui.fragments.TopScorersFragment
import java.lang.IllegalArgumentException

class StandingsPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TableFragment()
            1 -> TopScorersFragment()
            2 -> TopAssistsFragment()
            else -> throw IllegalArgumentException("No tabs found with position: $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Standings"
            1 -> "Top Scorers"
            2 -> "Top Assists"
            else -> throw IllegalArgumentException("No tabs found with position: $position")
        }
    }
}