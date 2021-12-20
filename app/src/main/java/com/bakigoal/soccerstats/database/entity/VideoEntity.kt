package com.bakigoal.soccerstats.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bakigoal.soccerstats.domain.Video

@Entity(tableName = "db_video")
data class VideoEntity(
    @PrimaryKey
    val url: String,
    val title: String,
    val description: String,
    val updated: String,
    val thumbnail: String
)

fun VideoEntity.toDomainModel(): Video = Video(
    url = this.url,
    title = this.title,
    description = this.description,
    updated = this.updated,
    thumbnail = this.thumbnail
)

fun List<VideoEntity>.toDomainModel(): List<Video> = map(VideoEntity::toDomainModel)