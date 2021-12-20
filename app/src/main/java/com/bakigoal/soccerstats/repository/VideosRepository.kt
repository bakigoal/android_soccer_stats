package com.bakigoal.soccerstats.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bakigoal.soccerstats.database.VideosDatabase
import com.bakigoal.soccerstats.database.entity.toDomainModel
import com.bakigoal.soccerstats.domain.Video
import com.bakigoal.soccerstats.network.Network
import com.bakigoal.soccerstats.network.dto.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching devbytes from the network and storing them on disk
 */
class VideosRepository(private val database: VideosDatabase) {

    val videos: LiveData<List<Video>> =
        Transformations.map(database.videoDao.getVideos()) { it.toDomainModel() }

    suspend fun refreshVideos() = withContext(Dispatchers.IO) {
        val playlist = Network.devbytes.getPlaylistAsync().await()
        database.videoDao.insertAll(*playlist.toEntity())
    }
}