package com.bakigoal.soccerstats.network.dto

import com.bakigoal.soccerstats.database.entity.CountryEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    val name: String,
    val code: String?,
    val flag: String?
)

/**
 * Convert Dto to database objects
 */
fun CountryDto.toEntity(): CountryEntity = CountryEntity(code = this.code, name = this.name, flag = this.flag)

fun List<CountryDto>.toEntity(): Array<CountryEntity> = map(CountryDto::toEntity).toTypedArray()