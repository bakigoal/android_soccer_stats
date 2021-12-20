package com.bakigoal.soccerstats.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bakigoal.soccerstats.domain.Country
import com.bakigoal.soccerstats.domain.League

@Entity(tableName = "db_league")
data class LeagueEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val logo: String,
    @Embedded
    val country: CountryEntity
)

data class CountryEntity(
    val countryName: String,
    val countryCode: String?,
    val countryFlag: String?
)


fun LeagueEntity.toDomainModel(): League = League(
    id,
    name,
    type,
    logo,
    Country(country.countryName, country.countryCode, country.countryFlag)
)