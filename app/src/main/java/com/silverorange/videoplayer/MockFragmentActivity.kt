package com.silverorange.videoplayer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.silverorange.videoplayer.fragment.NetworkErrorFragment
import com.silverorange.videoplayer.network.MockNetworkHelper
import com.silverorange.videoplayer.util.MockConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class MockFragmentActivity : FragmentActivity() {

    @Inject lateinit var mockNetworkHelper: MockNetworkHelper

    //Fragment Helper Functions:
    //--------------------------
    private fun isFragmentOnStack(fragmentTag: String): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        return fragment != null
    }

    fun getFragmentOnStack(fragmentTag: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(fragmentTag)
    }

    //Fragment Transactions:
    //----------------------
    private fun addFragment(fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.commit(true) {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            add(android.R.id.content, fragment, fragmentTag)
            setReorderingAllowed(true)
            addToBackStack(fragmentTag)
        }
    }

    fun addFragmentIfNotOnStack(fragment: Fragment, fragmentTag: String) {
        if (isFragmentOnStack(fragmentTag)) { return }
        addFragment(fragment, fragmentTag)
    }

    fun showMockFragment(fragmentTag: String): Boolean {
        if (isFragmentOnStack(fragmentTag)) { return false }
        when (fragmentTag) {
            MockConstants.FRAGMENT_NETWORK_ERROR_TAG -> {
                if (mockNetworkHelper.isConnectedToInternet()) { return false }
                addFragment(NetworkErrorFragment(), fragmentTag)
                return true
            }
            else -> return false
        }
    }
}
