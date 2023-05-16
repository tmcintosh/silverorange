package com.silverorange.videoplayer.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.InvocationTargetException

object MockUtilsInput {
    //The soft keyboard is terrible. See https://rmirabelle.medium.com/close-hide-the-soft-keyboard-in-android-db1da22b09d2
    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) { view = View(activity) }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //super hacky.  Android R has a better method: view.rootWindowInsets.getInsets(Type.ime()).isVisible
    @Suppress("unused")
    fun isKeyboardVisible(context: Context): Boolean {
        return getKeyboardHeight(context) > 0
    }

    private fun getKeyboardHeight(context: Context): Int {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var height = 0
        try {
            val windowHeightMethod = InputMethodManager::class.java.getMethod("getInputMethodWindowVisibleHeight")
            height = windowHeightMethod.invoke(inputMethodManager) as Int
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return height
    }
}
