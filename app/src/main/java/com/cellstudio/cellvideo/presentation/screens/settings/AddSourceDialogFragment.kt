package com.cellstudio.cellvideo.presentation.screens.settings

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.FragmentAddSourceBinding
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractor
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.settings.AddSourceViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.settings.AddSourceViewModelImpl
import com.cellstudio.cellvideo.presentation.base.BaseInjectorDialogFragment
import com.cellstudio.cellvideo.presentation.screens.loading.LoadingDialogFragment
import javax.inject.Inject

class AddSourceDialogFragment: BaseInjectorDialogFragment() {
    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    var listener: Listener ?= null

    private val loadingDialogFragment = LoadingDialogFragment.newInstance()
    private lateinit var viewModel: AddSourceViewModel
    private lateinit var labelName: String
    private lateinit var sourceUrl: String

    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_source
    }

    override fun onGetInputData() {
        super.onGetInputData()

        labelName = arguments?.getString(EXTRA_LABEL_NAME)?: ""
        sourceUrl = arguments?.getString(EXTRA_SOURCE_URL)?: ""
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
    }

    override fun onSetupViewModel() {
        super.onSetupViewModel()
        viewModel = ViewModelProvider(this,viewModelFactory).get(AddSourceViewModelImpl::class.java)
    }

    override fun onBindView(view: View) {
        super.onBindView(view)

        val binding = DataBindingUtil.bind<FragmentAddSourceBinding>(view)
        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this

//        setupErrorView("")
    }

    override fun observeResponse() {
        super.observeResponse()
        viewModel.getViewData().loading.observe(this, Observer{
            if (it) {
                loadingDialogFragment.show(childFragmentManager, null)
            } else {
                loadingDialogFragment.dismiss()
            }
        })

        viewModel.getViewData().getIsSourceDataReal().observe(this, Observer {
            listener?.addSource(it)
            this.dismiss()
        })
    }

    interface Listener {
        fun addSource(dataSource: DataSourcePresentationModel)
    }

    companion object {
        private const val EXTRA_LABEL_NAME = "EXTRA_LABEL_NAME"
        private const val EXTRA_SOURCE_URL = "EXTRA_SOURCE_URL"
        fun newInstance(labelName: String?= null, sourceUrl: String?= null): AddSourceDialogFragment {
            val args = Bundle()
            labelName?.let { args.putString(EXTRA_LABEL_NAME, labelName)}
            sourceUrl?.let { args.putString(EXTRA_SOURCE_URL, sourceUrl)}
            val fragment = AddSourceDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}