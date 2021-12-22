package com.bakigoal.soccerstats.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentStandingsBinding

class StandingsFragment : Fragment() {

    private val viewModel: StandingsViewModel by lazy {
        ViewModelProvider(this)[StandingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentStandingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_standings, container, false
        )

        val args = StandingsFragmentArgs.fromBundle(requireArguments())

        binding.league = args.league
        binding.season = args.season

        return binding.root
    }

}