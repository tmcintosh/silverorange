package com.silverorange.videoplayer.network

import com.silverorange.videoplayer.network.dto.VideosDTO

interface MockNetworkDataSource {
    suspend fun getVideos(): VideosDTO
}
