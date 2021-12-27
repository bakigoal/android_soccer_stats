package com.bakigoal.soccerstats.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentTeamBinding
import com.bakigoal.soccerstats.domain.SquadPlayer
import com.bakigoal.soccerstats.ui.adapters.SquadPlayerAdapter
import com.bakigoal.soccerstats.ui.viewModels.TeamViewModel
import com.google.android.material.snackbar.Snackbar

class TeamFragment : Fragment() {

    private lateinit var viewModel: TeamViewModel
    private lateinit var squadPlayerAdapter: SquadPlayerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val binding: FragmentTeamBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val teamId = TeamFragmentArgs.fromBundle(requireArguments()).teamId

        viewModel = ViewModelProvider(
            this, TeamViewModel.Factory(requireActivity().application, teamId)
        )[TeamViewModel::class.java]

        binding.viewModel = viewModel

        squadPlayerAdapter = SquadPlayerAdapter(SquadPlayerAdapter.OnClick { playerClicked(it)})
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = squadPlayerAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.squad.observe(viewLifecycleOwner, {
            it?.apply {
                squadPlayerAdapter.players = it.players
            }
        })
    }

    private fun playerClicked(player: SquadPlayer) {
        Snackbar.make(requireView(), "Clicked ${player.name}", Snackbar.LENGTH_SHORT).show()
    }
}