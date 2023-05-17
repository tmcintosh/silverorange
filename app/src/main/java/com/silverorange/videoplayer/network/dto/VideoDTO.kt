package com.silverorange.videoplayer.network.dto

import kotlinx.serialization.Serializable
@Serializable
data class VideosDTO(
    val videos: List<VideoDTO>
)

@Serializable
data class VideoDTO(
    val data: String,
    val data2: Boolean
)
