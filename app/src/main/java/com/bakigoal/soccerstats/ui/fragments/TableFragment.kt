package com.bakigoal.soccerstats.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentTableBinding
import com.bakigoal.soccerstats.domain.StandingTeam
import com.bakigoal.soccerstats.domain.Standings
import com.bakigoal.soccerstats.ui.adapters.TableAdapter
import com.bakigoal.soccerstats.ui.viewModels.TableViewModel
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentTableBinding>(
            inflater,
            R.layout.fragment_table,
            container,
            false
        )
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
                tableAdapter.teams = it.standings
            }
        })
    }

    private fun teamClicked(team: StandingTeam) {
        Snackbar.make(requireView(), "Clicked ${team.teamName}", Snackbar.LENGTH_LONG).show()
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