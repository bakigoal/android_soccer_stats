package com.bakigoal.soccerstats.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDto(
    val id: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val birth: BirthDto,
    val nationality: String,
    val height: String,
    val weight: String,
    val injured: Boolean? = false,
    val photo: String
)

data class BirthDto(
    val date: String,
    val place: String?,
    val country: String?
)
