package com.silverorange.videoplayer.repository

import com.silverorange.videoplayer.model.MockVideo
import com.silverorange.videoplayer.network.MockRetrofitNetwork
import com.silverorange.videoplayer.network.dto.asExternalModel
import javax.inject.Inject

class VideosRepository@Inject constructor(private val network: MockRetrofitNetwork) : IVideosRepository {
    override suspend fun fetchVideos(): List<MockVideo> {
        val videoDTOs = network.getVideos()
        return videoDTOs.map { response ->
            response.asExternalModel()
        }
    }
}
