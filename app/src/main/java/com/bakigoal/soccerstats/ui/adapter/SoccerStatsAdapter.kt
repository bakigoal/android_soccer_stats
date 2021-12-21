package com.bakigoal.soccerstats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.LeagueItemBinding
import com.bakigoal.soccerstats.domain.League

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class SoccerStatsAdapter(private val callback: LeagueClick) :
    RecyclerView.Adapter<SoccerStatsAdapter.CountryViewHolder>() {

    class LeagueClick(val apply: (League) -> Unit) {
        fun onClick(league: League) = apply(league)
    }

    class CountryViewHolder(val viewDataBinding: LeagueItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.league_item
        }
    }

    var leagues: List<League?> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = leagues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding: LeagueItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CountryViewHolder.LAYOUT,
            parent,
            false
        )
        return CountryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.league = leagues[position]
            it.leagueCallback = callback
        }
    }

}