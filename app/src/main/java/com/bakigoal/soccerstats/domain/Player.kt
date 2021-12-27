package com.bakigoal.soccerstats.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val id: String,
    val playerId: Int,
    val year: Int,
    val leagueId: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val birth: Birth,
    val nationality: String,
    val height: String?,
    val weight: String?,
    val injured: Boolean? = false,
    val photo: String
) : Parcelable

@Parcelize
data class Birth(
    val date: String,
    val place: String?,
    val country: String?
) : Parcelable
