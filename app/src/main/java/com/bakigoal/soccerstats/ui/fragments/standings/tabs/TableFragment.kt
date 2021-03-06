package com.bakigoal.soccerstats.ui.fragments.standings.tabs

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentStandingsTableBinding
import com.bakigoal.soccerstats.domain.StandingTeam
import com.bakigoal.soccerstats.ui.adapters.TableAdapter
import com.bakigoal.soccerstats.ui.fragments.standings.StandingsFragmentDirections
import com.bakigoal.soccerstats.ui.viewModels.TableViewModel
import com.bakigoal.soccerstats.util.populateColors
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "leagueId"
private const val ARG_PARAM2 = "year"

class TableFragment : Fragment() {
    private var leagueId: Int? = null
    private var year: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leagueId = it.getString(ARG_PARAM1)?.toInt()
            year = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var viewModel: TableViewModel
    private lateinit var tableAdapter: TableAdapter
    private lateinit var binding: FragmentStandingsTableBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_standings_table, container, false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(
            this,
            TableViewModel.Factory(requireActivity().application, leagueId!!, year!!)
        )[TableViewModel::class.java]

        binding.viewModel = viewModel

        tableAdapter = TableAdapter(TableAdapter.OnClick { teamClicked(it) })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tableAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.standings.observe(viewLifecycleOwner, {
            it?.apply {
                it.populateColors()
                tableAdapter.teams = it.standings
                addColorsFooter(it.standings)
            }
        })
    }

    private fun addColorsFooter(standings: List<StandingTeam>) {
        val set = mutableSetOf<String>()
        standings.filter { it.descriptionColor != null }.forEach {
            val descriptionColor = it.descriptionColor!!
            if (!set.contains(descriptionColor)) {
                binding.tableContainer.addView(createColorView(it))
                set.add(descriptionColor)
            }
        }
    }

    private fun createColorView(team: StandingTeam): View {
        val layout = LinearLayout(requireContext())
        layout.orientation = HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        layoutParams.setMargins(24, 8, 24, 4)
        layout.gravity = Gravity.CENTER_VERTICAL
        layout.layoutParams = layoutParams

        val colorTextView = TextView(requireContext())
        colorTextView.setBackgroundResource(R.drawable.rounder_corner_2)
        val gradientDrawable = colorTextView.background as GradientDrawable
        gradientDrawable.setColor(Color.parseColor(team.descriptionColor))
        colorTextView.width = 24
        colorTextView.textSize = 6f
        val params = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        params.setMargins(0, 0, 24, 0)
        colorTextView.layoutParams = params
        layout.addView(colorTextView)

        val titleTextView = TextView(requireContext())
        titleTextView.text = team.description
        layout.addView(titleTextView)

        return layout
    }

    private fun teamClicked(team: StandingTeam) {
        findNavController().navigate(
            StandingsFragmentDirections.actionStandingsFragmentToTeamFragment(team.teamId, team.teamName)
        )
    }

    companion object {

        @JvmStatic
        fun newInstance(leagueId: Int, year: String) =
            TableFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, leagueId.toString())
                    putString(ARG_PARAM2, year)
                }
            }
    }
}