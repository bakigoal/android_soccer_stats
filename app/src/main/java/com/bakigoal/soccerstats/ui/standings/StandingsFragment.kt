package com.bakigoal.soccerstats.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentStandingsBinding
import com.bakigoal.soccerstats.domain.League
import com.bakigoal.soccerstats.domain.Season

class StandingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: StandingsViewModel by lazy {
        ViewModelProvider(this)[StandingsViewModel::class.java]
    }
    private lateinit var currentLeague: League
    private lateinit var seasonSpinner: Spinner
    private lateinit var seasonList: List<Season>
    private var currentSeasonPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentStandingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_standings, container, false
        )

        val args = StandingsFragmentArgs.fromBundle(requireArguments())

        currentLeague = args.league
        seasonList = args.league.seasons.sortedWith(compareByDescending { it.year })
        currentSeasonPosition = seasonList.indexOf(args.season)

        binding.league = currentLeague
        binding.season = args.season

        seasonSpinner = binding.seasonSpinner
        setupSpinner()

        return binding.root
    }

    private fun setupSpinner() {
        val array = seasonList.map { it.seasonText() }.toTypedArray()
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, array)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        seasonSpinner.adapter = adapter
        seasonSpinner.setSelection(currentSeasonPosition)
        seasonSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (currentSeasonPosition != position){
            val actionStandingsFragmentSelf =
                StandingsFragmentDirections.actionStandingsFragmentSelf(
                    currentLeague,
                    currentLeague.name,
                    seasonList[position]
                )
            findNavController().navigate(actionStandingsFragmentSelf)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}