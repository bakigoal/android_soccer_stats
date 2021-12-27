package com.bakigoal.soccerstats.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerInfo(
    val player: Player,
    val playerStats: PlayerStats,
    var scorerPosition: Int = -1
) : Parcelable