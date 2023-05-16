package com.silverorange.videoplayer.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.silverorange.videoplayer.util.MockConstants

class MockProgressBar(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs, 0) {
    private val showAnimationDelayHandler: Handler = Handler(Looper.getMainLooper())
    private var isAnimatingShow = false
    private var isAnimatingHide = false
    var listener: OnMockProgressBarShowOnAnimationStartListener? = null

    interface OnMockProgressBarShowOnAnimationStartListener {
        fun onMockProgressBarShowOnAnimationStart()
    }

    fun show() {
        showAnimationDelayHandler.postDelayed({ animateShow() }, MockConstants.NETWORK_TIME_TIL_PROGRESS_BAR_DELAY.toLong()) //give a delay before fading in the spinner
    }

    private fun animateShow() {
        if (!isAnimatingShow) {
            animate()
                .alpha(1f)
                .setDuration(MockConstants.ANIMATION_TIME_LONG.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animator: Animator) {
                        visibility = VISIBLE
                        isAnimatingShow = true
                        listener?.onMockProgressBarShowOnAnimationStart()
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        isAnimatingShow = false
                    }
                })
        }
    }

    @JvmOverloads
    fun hide(cancelShow: Boolean = true) {
        if (cancelShow) {
            showAnimationDelayHandler.removeCallbacksAndMessages(null)
        }

        if (!isAnimatingHide) {
            animate()
                .alpha(0f)
                .setDuration(MockConstants.ANIMATION_TIME_LONG.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animator: Animator) {
                        isAnimatingHide = true
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        visibility = INVISIBLE
                        isAnimatingHide = false
                    }
                })
        }
    }
}
