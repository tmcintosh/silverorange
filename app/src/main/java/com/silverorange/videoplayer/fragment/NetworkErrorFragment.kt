package com.silverorange.videoplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.silverorange.videoplayer.databinding.FragmentNetworkErrorBinding

class NetworkErrorFragment : MockFragment() {
    private var _binding: FragmentNetworkErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNetworkErrorBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        super.handleLandscapeSizing(binding.networkErrorCardView)
        val cancelButton = binding.networkErrorContentButtonsCancelButton
        cancelButton.setOnClickListener { handleCancel() }
        val connectButton = binding.networkErrorContentButtonsConnectButton
        connectButton.setOnClickListener { handleConnect() }
    }

    private fun handleCancel() {
        onBackPressedCallback.handleOnBackPressed()
    }

    private fun handleConnect() {
        val intent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
        startActivity(intent)
        onBackPressedCallback.handleOnBackPressed()
    }
}
