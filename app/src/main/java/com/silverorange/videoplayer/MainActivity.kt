package com.silverorange.videoplayer

import android.os.Bundle
import com.silverorange.videoplayer.fragment.MainFragment
import com.silverorange.videoplayer.util.MockConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : MockFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragmentIfNotOnStack(MainFragment(), MockConstants.FRAGMENT_MAIN_TAG)
    }
}
