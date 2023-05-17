package com.silverorange.videoplayer.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue

object MockUtils {
    @Suppress("unused")
    fun isInDarkMode(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

    fun dpToPixels(dp: Float, resources: Resources): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    }
}

