package com.silverorange.videoplayer.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.silverorange.videoplayer.util.MockConstants
import com.silverorange.videoplayer.util.MockUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MockFragment : Fragment() {
    companion object {
        val mockFragmentTags = listOf(
            MockConstants.FRAGMENT_NETWORK_ERROR_TAG
        )
    }

    lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) { requireActivity().supportFragmentManager.popBackStack() }; onBackPressedCallback.isEnabled = true //allow back pressed
    }

    fun handleLandscapeSizing(materialCardView: MaterialCardView) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val layoutParams: ViewGroup.LayoutParams = materialCardView.layoutParams
            layoutParams.width = MockUtils.dpToPixels(MockConstants.LANDSCAPE_FRAGMENT_WIDTH, resources)
            materialCardView.layoutParams = layoutParams
        }
    }
}
