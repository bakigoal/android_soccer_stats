package com.bakigoal.soccerstats.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.TopPlayersItemBinding
import com.bakigoal.soccerstats.domain.PlayerInfo

class TopPlayersAdapter(private val callback: OnClick) :
    RecyclerView.Adapter<TopPlayersAdapter.CountryViewHolder>() {

    class OnClick(val apply: (PlayerInfo) -> Unit) {
        fun onClick(playerInfo: PlayerInfo) = apply(playerInfo)
    }

    class CountryViewHolder(val viewDataBinding: TopPlayersItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.top_players_item
        }
    }

    var players: List<PlayerInfo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = players.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding: TopPlayersItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CountryViewHolder.LAYOUT,
            parent,
            false
        )
        return CountryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.playerInfo = players[position]
            it.callback = callback
        }
    }

}