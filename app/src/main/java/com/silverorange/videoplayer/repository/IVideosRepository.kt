package com.silverorange.videoplayer.repository

import com.silverorange.videoplayer.MockVideo

interface IVideosRepository {
    suspend fun fetchVideos(): List<MockVideo>?
}

