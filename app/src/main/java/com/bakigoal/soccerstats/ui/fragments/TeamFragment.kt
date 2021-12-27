package com.bakigoal.soccerstats.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentTeamBinding

class TeamFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val binding: FragmentTeamBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.team = TeamFragmentArgs.fromBundle(requireArguments()).team

        return binding.root
    }
}