package com.silverorange.videoplayer.model

import java.util.Date

//Represents a video that the user interacts with
class MockVideo(
    val id: String?,
    val title: String?,
    val hlsURL: String?,
    val fullURL: String?,
    val description: String?,
    val publishedAt: Date?,
    val authorId: String?,
    val authorName: String?
) {
    val fullDescription: String get() {
        return id + "\n" + title + "\n" + hlsURL + "\n" + fullURL + "\n" + this.description + "\n" + publishedAt.toString() + "\n" + authorId + "\n" + authorName
    }

    val uiDescription: String get() {
        return "**$title**\n\n$authorName\n\n$description"
    }
}
