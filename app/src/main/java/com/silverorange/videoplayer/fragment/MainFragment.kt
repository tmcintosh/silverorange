package com.silverorange.videoplayer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.silverorange.videoplayer.MockFragmentActivity
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.databinding.FragmentMainBinding
import com.silverorange.videoplayer.ui.MockProgressBar
import com.silverorange.videoplayer.util.MockConstants
import com.silverorange.videoplayer.util.MockUtilsVersioning
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val videosViewModel: VideosViewModel by viewModels()

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var loadingMockProgressBar: MockProgressBar
    private lateinit var downloadVideosDataButton: ImageButton

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
                videosViewModel.getVideosDataUiState.collect {
                    when (it) {
                        is GetVideosDataUiState.Success -> { loadingMockProgressBar.hide() ; onGetVideosDataSuccess() }
                        is GetVideosDataUiState.Error -> { loadingMockProgressBar.hide() ; onVideosDownloadError() }
                        is GetVideosDataUiState.Loading -> { loadingMockProgressBar.show() ; onVideosDownloadLoading() }
                        else -> {}
                    }
                }
            }
        }

        setupUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        binding.mainVersion.text = MockUtilsVersioning.getCurrentAppVersionFullString(requireContext())
        binding.mainCopyright.text = getString(R.string.copyright, Calendar.getInstance().get(Calendar.YEAR).toString())
        loadingMockProgressBar = binding.loadingCenterMockProgressBar
        downloadVideosDataButton = binding.mainContentDownloadVideosDataButton
        downloadVideosDataButton.setOnClickListener {
            getVideosData()
        }
    }

    private fun getVideosData() {
        val mockFragmentActivity = requireActivity() as MockFragmentActivity
        if (mockFragmentActivity.showMockFragment(MockConstants.FRAGMENT_NETWORK_ERROR_TAG)) { return }
        videosViewModel.getVideosData()
    }

    private fun onVideosDownloadLoading() {
    }

    private fun onGetVideosDataSuccess() {
    }

    private fun onVideosDownloadError() {
    }
}
