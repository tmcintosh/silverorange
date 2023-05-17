package com.silverorange.videoplayer.network

import com.silverorange.videoplayer.network.dto.VideoDTO

interface MockNetworkDataSource {
    suspend fun getVideos(): List<VideoDTO>
}
