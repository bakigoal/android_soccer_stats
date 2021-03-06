package com.bakigoal.soccerstats.ui.fragments.standings.tabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentStandingsTopPlayersBinding
import com.bakigoal.soccerstats.domain.PlayerInfo
import com.bakigoal.soccerstats.domain.PlayerStatType
import com.bakigoal.soccerstats.ui.adapters.TopPlayersAdapter
import com.bakigoal.soccerstats.ui.fragments.standings.StandingsFragmentDirections
import com.bakigoal.soccerstats.ui.viewModels.TopPlayersViewModel
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "leagueId"
private const val ARG_PARAM2 = "year"
private const val ARG_PARAM3 = "type"

class TopPlayersFragment : Fragment() {
    private var leagueId: Int? = null
    private var year: String? = null
    private var type: PlayerStatType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leagueId = it.getString(ARG_PARAM1)?.toInt()
            year = it.getString(ARG_PARAM2)
            it.getString(ARG_PARAM3)?.apply {
                type = PlayerStatType.valueOf(this)
            }
        }
    }

    private lateinit var viewModel: TopPlayersViewModel
    private lateinit var tableAdapter: TopPlayersAdapter
    private lateinit var binding: FragmentStandingsTopPlayersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_standings_top_players,
            container,
            false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(
            this,
            TopPlayersViewModel.Factory(requireActivity().application, leagueId!!, year!!, type!!)
        )[TopPlayersViewModel::class.java]

        binding.viewModel = viewModel

        tableAdapter = TopPlayersAdapter(TopPlayersAdapter.OnClick { teamClicked(it) })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tableAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.players.observe(viewLifecycleOwner, {
            it?.apply {
                tableAdapter.players = it
                Log.i("players", "players: $it")
            }
        })
    }

    private fun teamClicked(playerInfo: PlayerInfo) {
        findNavController().navigate(
            StandingsFragmentDirections.actionStandingsFragmentToPlayerFragment(
                playerInfo,
                playerInfo.player.name
            )
        )
    }

    companion object {

        @JvmStatic
        fun newInstance(leagueId: Int, year: String, type: PlayerStatType) =
            TopPlayersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, leagueId.toString())
                    putString(ARG_PARAM2, year)
                    putString(ARG_PARAM3, type.name)
                }
            }
    }
}