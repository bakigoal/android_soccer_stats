package com.bakigoal.soccerstats.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.TableTeamItemBinding
import com.bakigoal.soccerstats.databinding.TopScorersPlayerItemBinding
import com.bakigoal.soccerstats.domain.StandingTeam

class TopScorersAdapter(private val callback: OnClick) :
    RecyclerView.Adapter<TopScorersAdapter.CountryViewHolder>() {

    class OnClick(val apply: (StandingTeam) -> Unit) {
        fun onClick(team: StandingTeam) = apply(team)
    }

    class CountryViewHolder(val viewDataBinding: TopScorersPlayerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.top_scorers_player_item
        }
    }

    var teams: List<StandingTeam?> = emptyList()
        set(value) {
            field = sort(value)
            notifyDataSetChanged()
        }

    private fun sort(list: List<StandingTeam?>) = list.sortedWith(compareBy { it?.rank })

    override fun getItemCount() = teams.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding: TopScorersPlayerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CountryViewHolder.LAYOUT,
            parent,
            false
        )
        return CountryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.team = teams[position]
            it.callback = callback
        }
    }

}