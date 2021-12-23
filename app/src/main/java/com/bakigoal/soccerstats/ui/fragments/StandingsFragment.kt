package com.bakigoal.soccerstats.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentStandingsBinding
import com.bakigoal.soccerstats.domain.Season
import com.bakigoal.soccerstats.ui.viewModels.StandingsViewModel
import com.google.android.material.tabs.TabLayout

class StandingsFragment : Fragment(), AdapterView.OnItemSelectedListener, TabLayout.OnTabSelectedListener {

    private lateinit var binding: FragmentStandingsBinding
    private lateinit var viewModel: StandingsViewModel
    private lateinit var seasonSpinner: Spinner
    private var currentSeasonPosition: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_standings, container, false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        val (league, _, seasonPosition) = StandingsFragmentArgs.fromBundle(requireArguments())

        viewModel = ViewModelProvider(
            this,
            StandingsViewModel.Factory(league, seasonPosition)
        )[StandingsViewModel::class.java]

        currentSeasonPosition = seasonPosition
        binding.league = league
        binding.season = league.sortedSeasons()[seasonPosition]

        seasonSpinner = binding.seasonSpinner

        setupTabs()

        return binding.root
    }

    private fun setupTabs() {
        binding.standingsTabs.addOnTabSelectedListener(this)
        selectStandingsTab()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.seasonList.observe(viewLifecycleOwner, {
            setupSpinner(it)
        })
        viewModel.navigateToSeason.observe(viewLifecycleOwner, {
            it?.apply {
                changeSeason(it)
                viewModel.doneNavigateToSeason()
            }
        })
    }

    private fun setupSpinner(seasonList: List<Season>) {
        val array = seasonList.map { it.seasonText() }.toTypedArray()
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireActivity(), R.layout.season_spinner_item, array)
        adapter.setDropDownViewResource(R.layout.season_spinner_dropdown_item)
        seasonSpinner.adapter = adapter
        seasonSpinner.setSelection(currentSeasonPosition)
        seasonSpinner.onItemSelectedListener = this
    }

    private fun changeSeason(seasonPosition: Int) {
        val actionStandingsFragmentSelf =
            StandingsFragmentDirections.actionStandingsFragmentSelf(
                binding.league!!,
                binding.league!!.name,
                seasonPosition
            )
        findNavController().navigate(actionStandingsFragmentSelf)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
        viewModel.seasonSelected(position)

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> selectStandingsTab()
            1 -> selectTopScorersTab()
            2 -> selectTopAssistsTab()
        }
    }

    private fun selectStandingsTab() {
        val year = binding.league!!.sortedSeasons()[currentSeasonPosition].year
        val tableFragment = TableFragment.newInstance(binding.league!!.id, year)
        replaceTab(tableFragment)
    }

    private fun selectTopScorersTab() {
        replaceTab(TopScorersFragment())
    }

    private fun selectTopAssistsTab() {
        replaceTab(MatchesFragment())
    }

    private fun replaceTab(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .apply { replace(R.id.tabContainer, fragment) }
            .commit()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

}