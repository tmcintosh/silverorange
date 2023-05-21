package com.silverorange.videoplayer.repository

import com.silverorange.videoplayer.model.MockVideo

interface IVideosRepository {
    suspend fun fetchVideos(): List<MockVideo>?
}

