package com.bakigoal.soccerstats.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val binding: FragmentPlayerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.playerInfo = PlayerFragmentArgs.fromBundle(requireArguments()).playerInfo

        return binding.root
    }
}