package com.bakigoal.soccerstats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.CountryItemBinding
import com.bakigoal.soccerstats.domain.Country

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class SoccerStatsAdapter(private val callback: CountryClick) : RecyclerView.Adapter<SoccerStatsAdapter.CountryViewHolder>() {

    class CountryClick(val apply: (Country) -> Unit) {
        fun onClick(country: Country) = apply(country)
    }

    class CountryViewHolder(val viewDataBinding: CountryItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.country_item
        }
    }

    var countries: List<Country> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = countries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding: CountryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CountryViewHolder.LAYOUT,
            parent,
            false
        )
        return CountryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.country = countries[position]
            it.countryCallback = callback
        }
    }

}