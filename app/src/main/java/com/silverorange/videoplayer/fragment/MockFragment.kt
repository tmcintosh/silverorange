package com.silverorange.videoplayer.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MockFragment : Fragment() {
    lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) { requireActivity().supportFragmentManager.popBackStack() }; onBackPressedCallback.isEnabled = true //allow back pressed
    }
}
