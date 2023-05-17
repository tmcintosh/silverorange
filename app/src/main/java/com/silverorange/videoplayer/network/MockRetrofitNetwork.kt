package com.silverorange.videoplayer.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverorange.videoplayer.network.dto.VideosDTO
import com.silverorange.videoplayer.util.MockConstants.DEFAULT_RETROFIT_BASE_URL
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

private interface MockRetrofitNetworkApi {
    @GET("videos")
    suspend fun getVideos(): VideosDTO
}

@OptIn(ExperimentalSerializationApi::class)

class MockRetrofitNetwork @Inject constructor(networkJson: Json, okhttpCallFactory: Call.Factory) : MockNetworkDataSource {
    private val contentType = "application/json".toMediaType()

    private val networkApi = Retrofit.Builder()
        .baseUrl(DEFAULT_RETROFIT_BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory(contentType))
        .build()
        .create(MockRetrofitNetworkApi::class.java)

    override suspend fun getVideos(): VideosDTO = networkApi.getVideos()
}
