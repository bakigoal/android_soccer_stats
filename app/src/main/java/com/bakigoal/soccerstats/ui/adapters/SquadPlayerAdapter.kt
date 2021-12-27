package com.bakigoal.soccerstats.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.TableTeamPlayerItemBinding
import com.bakigoal.soccerstats.domain.SquadPlayer
import kotlin.math.max

class SquadPlayerAdapter(private val callback: OnClick) :
    RecyclerView.Adapter<SquadPlayerAdapter.CountryViewHolder>() {

    class OnClick(val apply: (SquadPlayer) -> Unit) {
        fun onClick(player: SquadPlayer) = apply(player)
    }

    class CountryViewHolder(val viewDataBinding: TableTeamPlayerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.table_team_player_item
        }
    }

    var players: List<SquadPlayer?> = emptyList()
        set(value) {
            field = sort(value)
            notifyDataSetChanged()
        }

    private fun sort(list: List<SquadPlayer?>): List<SquadPlayer?> {
        val map = mutableMapOf<String, ArrayList<SquadPlayer>>()
        list.forEach {
            val key = it?.position ?: "-"
            if (!map.containsKey(key)) {
                map[key] = ArrayList()
            }
            map[key]?.add(it!!)
        }

        val result = ArrayList<SquadPlayer>()
        map["Goalkeeper"]?.apply { result.addAll(this.toList()) }
        map.remove("Goalkeeper")
        map["Defender"]?.apply { result.addAll(this.toList()) }
        map.remove("Defender")
        map["Midfielder"]?.apply { result.addAll(this.toList()) }
        map.remove("Midfielder")
        map["Attacker"]?.apply { result.addAll(this.toList()) }
        map.remove("Attacker")

        map.values.forEach { result.addAll(it) }

        return result
    }

    override fun getItemCount() = players.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding: TableTeamPlayerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CountryViewHolder.LAYOUT,
            parent,
            false
        )
        return CountryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.player = players[position]
            it.onclick = callback
        }
    }

}