package com.bakigoal.soccerstats.ui.fragments.standings

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
import com.bakigoal.soccerstats.domain.PlayerStatType
import com.bakigoal.soccerstats.domain.Season
import com.bakigoal.soccerstats.ui.fragments.standings.tabs.TableFragment
import com.bakigoal.soccerstats.ui.fragments.standings.tabs.TopPlayersFragment
import com.bakigoal.soccerstats.ui.viewModels.StandingsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class StandingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

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
        val chipGroup = binding.chipGroup
        chipGroup.addView(createChip(chipGroup, StandingsChip.STANDINGS, true))
        chipGroup.addView(createChip(chipGroup, StandingsChip.TOP_SCORERS))
        chipGroup.addView(createChip(chipGroup, StandingsChip.TOP_ASSISTS))
    }

    private fun createChip(chipGroup: ChipGroup, standingsChip: StandingsChip, checked: Boolean = false): Chip {
        val inflater = LayoutInflater.from(chipGroup.context)
        val chip = inflater.inflate(R.layout.standings_chip, chipGroup, false) as Chip
        chip.text = standingsChip.text
        chip.tag = standingsChip.tag
        chip.isChecked = checked
        chip.setOnCheckedChangeListener { button, isChecked ->
            viewModel.changeTab(button.tag as Int, isChecked)
        }
        return chip
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
        viewModel.tabPosition.observe(viewLifecycleOwner, { changeTab(it) })
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

    private fun changeTab(tag: Int) {
        when (tag) {
            StandingsChip.STANDINGS.tag -> selectStandingsTab()
            StandingsChip.TOP_SCORERS.tag -> selectTopScorersTab()
            StandingsChip.TOP_ASSISTS.tag -> selectTopAssistsTab()
        }
    }

    private fun selectStandingsTab() {
        val year = binding.league!!.sortedSeasons()[currentSeasonPosition].year
        val tableFragment = TableFragment.newInstance(binding.league!!.id, year)
        replaceTab(tableFragment)
    }

    private fun selectTopScorersTab() {
        val year = binding.league!!.sortedSeasons()[currentSeasonPosition].year
        val tableFragment =
            TopPlayersFragment.newInstance(binding.league!!.id, year, PlayerStatType.GOAL)
        replaceTab(tableFragment)
    }

    private fun selectTopAssistsTab() {
        val year = binding.league!!.sortedSeasons()[currentSeasonPosition].year
        val tableFragment =
            TopPlayersFragment.newInstance(binding.league!!.id, year, PlayerStatType.ASSIST)
        replaceTab(tableFragment)
    }

    private fun replaceTab(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .apply { replace(R.id.tabContainer, fragment) }
            .commit()
    }
}

private enum class StandingsChip(val tag: Int, val text: String) {
    STANDINGS(0, "Standings"),
    TOP_SCORERS(1, "Top Scorers"),
    TOP_ASSISTS(2, "Top Assists")
}