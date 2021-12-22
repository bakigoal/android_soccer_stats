package com.bakigoal.soccerstats.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StandingTeam
    (
    val rank: Int,
    val points: Int,
    val form: String,
    val teamId: Int,
    val teamName: String,
    val teamLogo: String,
    val description: String?,
    val all: Stats,
    val home: Stats,
    val away: Stats
) : Parcelable

@Parcelize
data class Stats(
    val played: Int,
    val win: Int,
    val draw: Int,
    val lose: Int,
    val goalsFor: Int,
    val goalsAgainst: Int
) : Parcelable