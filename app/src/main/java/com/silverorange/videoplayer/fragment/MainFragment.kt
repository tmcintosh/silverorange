package com.silverorange.videoplayer.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.silverorange.videoplayer.MockFragmentActivity
import com.silverorange.videoplayer.MockVideo
import com.silverorange.videoplayer.databinding.FragmentMainBinding
import com.silverorange.videoplayer.ui.MockProgressBar
import com.silverorange.videoplayer.util.MockConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val TAG = MainFragment::class.java.simpleName

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val videosViewModel: VideosViewModel by viewModels()

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var loadingMockProgressBar: MockProgressBar
    private lateinit var downloadVideosDataButton: ImageButton

    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) { requireActivity().moveTaskToBack(true) }; onBackPressedCallback.isEnabled = true //allow back pressed
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainTopBar) { v: View, insets: WindowInsetsCompat ->
            val topPadding = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.setPadding(v.paddingStart, topPadding, v.paddingEnd, v.paddingBottom)
            insets
        }

        lifecycleScope.launch {
            launch {
                videosViewModel.getVideosUiState.collect {
                    when (it) {
                        is GetVideosUiState.Success -> { loadingMockProgressBar.hide() ; onGetVideosSuccess(it.mockVideos) }
                        is GetVideosUiState.Error -> { loadingMockProgressBar.hide() ; onGetVideosError() }
                        is GetVideosUiState.Loading -> { loadingMockProgressBar.show() ; onGetVideosLoading() }
                        else -> {}
                    }
                }
            }
        }

        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getVideos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        setupPlayerUI()
        binding.mainContentTextTextview.movementMethod = ScrollingMovementMethod()
        loadingMockProgressBar = binding.loadingCenterMockProgressBar
        downloadVideosDataButton = binding.mainContentDownloadVideosDataButton
        downloadVideosDataButton.setOnClickListener {
            getVideos()
        }
    }

    private fun setupPlayerUI() {
        player = ExoPlayer.Builder(requireContext()).build()
        player.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    mediaItem?.localConfiguration?.uri?.let { uri ->
                        val mockVideo = videosViewModel.findVideoForUri(uri)
                        mockVideo?.let { loadDescription(it) }
                    }
                    binding.mainContentErrorTextTextview.visibility = View.GONE
                }

                override fun onEvents(player: Player, events: Player.Events) {
                    if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED) || events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)) {
                        binding.mainContentErrorTextTextview.visibility = View.GONE
                    }
                    if (events.contains(Player.EVENT_PLAYER_ERROR)) {
                        binding.mainContentErrorTextTextview.visibility = View.VISIBLE
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    val cause = error.cause
                    if (cause is HttpDataSource.HttpDataSourceException) {
                        if (cause is HttpDataSource.InvalidResponseCodeException) {
                            Log.e(TAG, cause.responseCode.toString())
                            Log.e(TAG, cause.message.toString())
                        } else {
                            Log.e(TAG, cause.cause.toString())
                        }
                    }
                }
            }
        )

        playerView = binding.playerView
        playerView.player = player
    }

    private fun loadDescription(mockVideo: MockVideo) {
        binding.mainContentTextTextview.text = mockVideo.uiDescription
    }

    private fun getVideos() {
        val mockFragmentActivity = requireActivity() as MockFragmentActivity
        if (mockFragmentActivity.showMockFragment(MockConstants.FRAGMENT_NETWORK_ERROR_TAG)) {
            downloadVideosDataButton.visibility = View.VISIBLE
            return
        }

        downloadVideosDataButton.visibility = View.GONE
        videosViewModel.getVideos()
    }

    private fun onGetVideosLoading() {}

    private fun onGetVideosSuccess(mockVideos: List<MockVideo>?) {
        mockVideos?.forEach { mockVideo ->
            Log.i(TAG, mockVideo.fullDescription)
            mockVideo.fullURL?.let { fullURL ->
                val mediaItem = MediaItem.fromUri(fullURL.toUri()) // Build the media item.
                player.addMediaItem(mediaItem)
            }
        }
        player.prepare()
        player.pause()
    }

    private fun onGetVideosError() {}
}
