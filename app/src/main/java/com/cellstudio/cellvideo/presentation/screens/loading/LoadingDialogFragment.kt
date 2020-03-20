package com.cellstudio.cellvideo.presentation.screens.loading

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.presentation.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_loading.*

class LoadingDialogFragment: BaseDialogFragment() {
    override fun getLayoutResource(): Int {
        return R.layout.fragment_loading
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)

        (ivLoadingLoading.drawable as Animatable?)?.start()
    }

    companion object {
        fun newInstance(): LoadingDialogFragment {
            val args = Bundle()
            val fragment = LoadingDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}