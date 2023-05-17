package com.silverorange.videoplayer.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverorange.videoplayer.network.dto.VideoDTO
import com.silverorange.videoplayer.util.MockConstants.DEFAULT_MOCK_BASE_URL
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

private interface MockRetrofitNetworkApi {
    //TODO: TOM - swap back out for this.
//    @GET("videos")
//    suspend fun getVideos(): List<VideoDTO>

    @GET("silverorange_server_json.json")
    suspend fun getVideos(): List<VideoDTO>
}

@OptIn(ExperimentalSerializationApi::class)

class MockRetrofitNetwork @Inject constructor(networkJson: Json, okhttpCallFactory: Call.Factory) : MockNetworkDataSource {
    private val contentType = "application/json".toMediaType()

    private val networkApi = Retrofit.Builder()
//        .baseUrl(DEFAULT_RETROFIT_BASE_URL) //TODO: TOM - swap back out for this.
        .baseUrl(DEFAULT_MOCK_BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory(contentType))
        .build()
        .create(MockRetrofitNetworkApi::class.java)

    override suspend fun getVideos(): List<VideoDTO> = networkApi.getVideos()
}
