package com.bakigoal.soccerstats.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentSoccerLeaguesBinding
import com.bakigoal.soccerstats.domain.League
import com.bakigoal.soccerstats.ui.adapter.SoccerStatsAdapter
import com.bakigoal.soccerstats.ui.viewmodel.SoccerStatsViewModel
import com.google.android.material.snackbar.Snackbar

class SoccerLeaguesFragment : Fragment() {

    private val viewModel: SoccerStatsViewModel by lazy {
        val app = requireActivity().application
        ViewModelProvider(this, SoccerStatsViewModel.Factory(app))[SoccerStatsViewModel::class.java]
    }

    private lateinit var soccerStatsAdapter: SoccerStatsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val binding: FragmentSoccerLeaguesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_soccer_leagues, container, false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        soccerStatsAdapter =
            SoccerStatsAdapter(SoccerStatsAdapter.LeagueClick { leagueClicked(it) })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = soccerStatsAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.leagues.observe(viewLifecycleOwner, {
            it?.apply { soccerStatsAdapter.leagues = it }
        })

        viewModel.showError.observe(viewLifecycleOwner, { errorText ->
            if (errorText.isNotEmpty()) {
                showSnackbarWithText("Error: $errorText")
                viewModel.doneShowError()
            }
        })
    }

    private fun leagueClicked(league: League) {
        showSnackbarWithText("Selected: ${league.name}")
    }

    private fun showSnackbarWithText(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }
}