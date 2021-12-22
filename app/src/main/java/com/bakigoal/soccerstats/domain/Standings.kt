package com.bakigoal.soccerstats.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Standings(
    val leagueId: String,
    val season: String,
    val standings: List<StandingTeam>
) : Parcelable