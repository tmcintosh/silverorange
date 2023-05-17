package com.silverorange.videoplayer.repository

import android.util.Log
import com.silverorange.videoplayer.MockVideo
import com.silverorange.videoplayer.network.MockRetrofitNetwork
import com.silverorange.videoplayer.network.dto.asExternalModel
import javax.inject.Inject

class VideosRepository@Inject constructor(private val network: MockRetrofitNetwork) : IVideosRepository {
    private val TAG = VideosRepository::class.java.simpleName
    override suspend fun fetchVideos(): List<MockVideo>? {
        var mockVideos: List<MockVideo>? = null
        try {
            val videoDTOs = network.getVideos()
            mockVideos = videoDTOs.map { response ->
                response.asExternalModel()
            }
        } catch (exception: Exception) {
            Log.e(TAG, "EXCEPTION: fetchVideos(): ${exception.message}" + "\n" + exception.stackTraceToString()) //possibly have additional handling bubble down from here...
        }
        return mockVideos
    }
}
