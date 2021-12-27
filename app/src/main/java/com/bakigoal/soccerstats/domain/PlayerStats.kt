package com.bakigoal.soccerstats.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerStats(
    val id: String,
    val year: Int,
    val leagueId: Int,
    val teamId: Int,
    val teamName: String,
    val teamLogo: String,
    val total: Int,
    val assists: Int,
    val saves: Int
):Parcelable