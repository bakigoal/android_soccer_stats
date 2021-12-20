package com.bakigoal.soccerstats.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bakigoal.soccerstats.domain.Country

@Entity(tableName = "db_country")
data class CountryEntity(
    @PrimaryKey
    val name: String,
    val code: String?,
    val flag: String?
)

fun CountryEntity.toDomainModel(): Country = Country(
    code = this.code,
    name = this.name,
    flag = this.flag
)

fun List<CountryEntity>.toDomainModel(): List<Country> = map(CountryEntity::toDomainModel)