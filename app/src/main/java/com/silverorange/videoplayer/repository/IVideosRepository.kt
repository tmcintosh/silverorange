package com.silverorange.videoplayer.repository

interface IVideosRepository {
    suspend fun getVideosData(url: String): List<String>
}

