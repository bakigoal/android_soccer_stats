package com.bakigoal.soccerstats.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Season(

    val year: String,
    val start: String,
    val end: String,
    val current: Boolean,
) : Parcelable {

    fun seasonText(): String = "$year/${year.toInt() + 1}"
}