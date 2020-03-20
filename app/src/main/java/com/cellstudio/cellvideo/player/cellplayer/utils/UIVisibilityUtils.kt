package com.cellstudio.cellvideo.player.cellplayer.utils

import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

object UIVisibilityUtil {
    fun enableKeepScreenOn(activity: AppCompatActivity?) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun disableKeepScreenOn(activity: AppCompatActivity?) {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun hideStatusBar(activity: AppCompatActivity?) {
        if (activity != null) {
            activity.window
                .addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_SECURE)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
            // Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
            hideActionBar(activity)
        }
    }

    fun showStatusBar(activity: AppCompatActivity?) {
        if (activity != null) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    fun hideNavigationBar(activity: AppCompatActivity?) {
        if (activity != null) {
            activity.window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

    fun showNavigationBar(activity: AppCompatActivity?) {
        if (activity != null) {
            activity.window.decorView.systemUiVisibility = 0
        }
    }

    fun hideActionBar(activity: AppCompatActivity?) {
        if (activity == null) {
            return
        }
        val actionBar: ActionBar? = activity.supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }

    fun showActionBar(activity: AppCompatActivity?) {
        if (activity == null) {
            return
        }
        val actionBar: ActionBar? = activity.supportActionBar
        if (actionBar != null) {
            actionBar.show()
        }
    }

    fun disableWindowFullScreen(activity: AppCompatActivity?) {
        if (activity != null) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            showActionBar(activity)
            showStatusBar(activity)
        }
    }

    fun enableWindowFullScreen(activity: AppCompatActivity?) {
        if (activity != null) {
            activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
            hideActionBar(activity)
            hideStatusBar(activity)
        }
    }
}