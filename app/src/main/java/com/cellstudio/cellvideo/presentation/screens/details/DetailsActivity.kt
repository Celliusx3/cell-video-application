package com.cellstudio.cellvideo.presentation.screens.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseInjectorActivity() {

    private var model: VideoPresentationModel ?= null

    companion object {
        private const val EXTRA_DETAILS = "EXTRA_DETAILS"

        fun getCallingIntent(context: Context, model: VideoPresentationModel): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_DETAILS, model)
            return intent
        }
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        intent?.let {
            model = intent.getParcelableExtra(EXTRA_DETAILS)
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
            val fragment = DetailsFragment.newInstance(it)
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.flVpMain, fragment)
            fragmentTransaction.commit()
        }
    }


}
