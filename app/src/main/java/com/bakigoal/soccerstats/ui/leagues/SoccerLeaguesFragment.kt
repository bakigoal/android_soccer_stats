package com.bakigoal.soccerstats.ui.leagues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentSoccerLeaguesBinding
import com.bakigoal.soccerstats.domain.League
import com.google.android.material.snackbar.Snackbar

class SoccerLeaguesFragment : Fragment() {

    private val viewModel: SoccerLeaguesViewModel by lazy {
        val app = requireActivity().application
        ViewModelProvider(
            this,
            SoccerLeaguesViewModel.Factory(app)
        )[SoccerLeaguesViewModel::class.java]
    }

    private lateinit var soccerLeaguesAdapter: SoccerLeaguesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val binding: FragmentSoccerLeaguesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_soccer_leagues, container, false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        soccerLeaguesAdapter =
            SoccerLeaguesAdapter(SoccerLeaguesAdapter.LeagueClick { leagueClicked(it) })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = soccerLeaguesAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.leagues.observe(viewLifecycleOwner, {
            it?.apply { soccerLeaguesAdapter.leagues = it }
        })

        viewModel.showError.observe(viewLifecycleOwner, { errorText ->
            if (errorText.isNotEmpty()) {
                showSnackbarWithText("Error: $errorText")
                viewModel.doneShowError()
            }
        })
    }

    private fun leagueClicked(league: League) {
        findNavController().navigate(
            SoccerLeaguesFragmentDirections.actionSoccerFragmentToStandingsFragment(league, league.name)
        )
    }

    private fun showSnackbarWithText(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }
}