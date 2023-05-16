package com.silverorange.videoplayer.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.repository.VideosRepository
import com.silverorange.videoplayer.util.MockConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GetVideosDataUiState { data class Success(val videosData: List<String>) : GetVideosDataUiState() ; data class Error(val exception: Exception) : GetVideosDataUiState() ; object Loading : GetVideosDataUiState() }

@HiltViewModel
class VideosViewModel @Inject constructor(private val application: Application,
                                          savedStateHandle: SavedStateHandle,
                                          private val videosRepository: VideosRepository) : AndroidViewModel(application) {
    private val savedState = savedStateHandle

    var savedStateLoadedUrl: String? = savedStateHandle[MockConstants.SAVED_STATE_IMAGES_LOADED_URL]
        set(value) { field = value; savedState[MockConstants.SAVED_STATE_IMAGES_LOADED_URL] = value }

    private val _getVideosDataUiState : MutableSharedFlow<GetVideosDataUiState?> = MutableSharedFlow(0) // Backing property to avoid state updates from other classes
    val getVideosDataUiState : SharedFlow<GetVideosDataUiState?> get() = _getVideosDataUiState // The UI collects from this StateFlow to get its state updates

    fun getVideosDataFromURL() {
        savedStateLoadedUrl?.let {
            getVideosData(it)
        }
    }

    private fun getVideosData(imageUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        //scrape:
        try {
            _getVideosDataUiState.emit(GetVideosDataUiState.Loading)
            val imageUrls = videosRepository.getVideosData(imageUrl)
            _getVideosDataUiState.emit(GetVideosDataUiState.Success(imageUrls))
        } catch (exception: Exception) {
            if (exception !is CancellationException) {
                _getVideosDataUiState.emit(GetVideosDataUiState.Error(exception))
            }
        }
    }
}
