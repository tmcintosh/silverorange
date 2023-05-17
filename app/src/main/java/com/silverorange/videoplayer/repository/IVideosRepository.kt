package com.silverorange.videoplayer.repository

import com.silverorange.videoplayer.network.dto.VideosDTO

interface IVideosRepository {
    suspend fun fetchVideos(): VideosDTO?
}

