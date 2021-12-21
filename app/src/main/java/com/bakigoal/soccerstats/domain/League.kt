package com.bakigoal.soccerstats.domain

data class League(

    val id: Int,
    val name: String,
    val type: String,
    val logo: String,
    var country: Country? = null,
    var seasons: List<Season> = ArrayList()
)