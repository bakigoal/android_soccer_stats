package com.bakigoal.soccerstats.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class League(

    val id: Int,
    val name: String,
    val type: String,
    val logo: String,
    var country: Country,
    var seasons: List<Season> = ArrayList()

): Parcelable {
    fun currentSeason():Season = seasons.first { it.current }
}