package com.bakigoal.soccerstats.domain

data class League(

    val id: String,
    val name: String,
    val type: String,
    val logo: String,
    var country: Country? = null
)