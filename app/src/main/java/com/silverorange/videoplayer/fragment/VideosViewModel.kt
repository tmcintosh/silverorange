package com.silverorange.videoplayer.fragment

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.MockVideo
import com.silverorange.videoplayer.repository.VideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GetVideosUiState { data class Success(val mockVideos: List<MockVideo>?) : GetVideosUiState() ; data class Error(val exception: Exception) : GetVideosUiState() ; object Loading : GetVideosUiState() }

@HiltViewModel
class VideosViewModel @Inject constructor(private val application: Application,
                                          savedStateHandle: SavedStateHandle,
                                          private val videosRepository: VideosRepository) : AndroidViewModel(application) {
    private val savedState = savedStateHandle

    private val _getVideosUiState : MutableSharedFlow<GetVideosUiState?> = MutableSharedFlow(0) // Backing property to avoid state updates from other classes
    val getVideosUiState : SharedFlow<GetVideosUiState?> get() = _getVideosUiState // The UI collects from this StateFlow to get its state updates

    var mockVideos: List<MockVideo>? = emptyList()

    fun getVideos() = viewModelScope.launch {
        try {
            _getVideosUiState.emit(GetVideosUiState.Loading)
            mockVideos = videosRepository.fetchVideos()
            mockVideos?.sortedBy { it.publishedAt } //sort by date
            _getVideosUiState.emit(GetVideosUiState.Success(mockVideos))
        } catch (exception: Exception) {
            if (exception !is CancellationException) {
                _getVideosUiState.emit(GetVideosUiState.Error(exception))
            }
        }
    }

    fun findVideoForUri(uri: Uri): MockVideo? {
        return mockVideos?.find { it.fullURL?.toUri() == uri }
    }
}
