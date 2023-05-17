package com.silverorange.videoplayer.network.dto

import com.silverorange.videoplayer.MockVideo
import com.silverorange.videoplayer.util.MockUtils
import kotlinx.serialization.Serializable

@Serializable
data class VideoDTO(
    val id: String,
    val title: String,
    val hlsURL: String,
    val fullURL: String,
    val description: String,
    val publishedAt: String,
    val author: AuthorDTO
)

@Serializable
data class AuthorDTO(
    val id: String,
    val name: String
)

fun VideoDTO.asExternalModel() = MockVideo(
    id = id,
    title = title,
    hlsURL = hlsURL,
    fullURL = fullURL,
    description = description,
    publishedAt = MockUtils.stringToDate(publishedAt),
    authorId = author.id,
    authorName = author.name
)
