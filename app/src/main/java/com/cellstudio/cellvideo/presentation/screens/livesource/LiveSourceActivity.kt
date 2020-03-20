package com.cellstudio.cellvideo.presentation.screens.livesource

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import kotlinx.android.synthetic.main.activity_details.*

class LiveSourceActivity : BaseInjectorActivity() {

    private var model: String?= null

    companion object {
        private const val EXTRA_LIVE_SOURCE = "EXTRA_LIVE_SOURCE"

        fun getCallingIntent(context: Context, model: String): Intent {
            val intent = Intent(context, LiveSourceActivity::class.java)
            intent.putExtra(EXTRA_LIVE_SOURCE, model)
            return intent
        }
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        intent?.let {
            model = intent.getStringExtra(EXTRA_LIVE_SOURCE)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_details
    }

    override fun getRootView(): View {
        return llDetailsRoot
    }

    override fun onBindView() {
        super.onBindView()
        model?.let {
            //            val fragment = VideoPlayerFragment.newInstance("")
            val fragment = LiveSourceFragment.newInstance(it)
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.flVpMain, fragment)
            fragmentTransaction.commit()
        }
    }


}
