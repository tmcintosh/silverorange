package com.silverorange.videoplayer.repository

import com.silverorange.videoplayer.network.MockNetworkHelper
import javax.inject.Inject

class VideosRepository@Inject constructor(private val mockNetworkHelper: MockNetworkHelper) : IVideosRepository {
    private val TAG = VideosRepository::class.java.simpleName

    override suspend fun getVideosData(url: String): List<String> {
        //TODO: TOM - call into retrofit
        return emptyList()
    }
}
