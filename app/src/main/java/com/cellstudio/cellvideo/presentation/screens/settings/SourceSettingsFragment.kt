package com.cellstudio.cellvideo.presentation.screens.settings

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.FragmentSourceSettingsBinding
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractor
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.settings.SourceSettingsViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.settings.SourceSettingsViewModelImpl
import com.cellstudio.cellvideo.presentation.adapters.SourceAdapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorBottomSheetDialogFragment
import com.cellstudio.cellvideo.presentation.screens.alertdialogs.DialogUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_source_settings.*
import javax.inject.Inject


class SourceSettingsFragment : BaseInjectorBottomSheetDialogFragment() {
    var listener: Listener?= null

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    private lateinit var viewModel: SourceSettingsViewModel
    private lateinit var adapter: SourceAdapter
    private var initialSource: DataSourcePresentationModel ?= null
    private var binding: FragmentSourceSettingsBinding? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_source_settings
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        viewModel.getViewEvent().startScreen()
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheetInternal?.let {
                val maxHeight = Resources.getSystem().displayMetrics.heightPixels - Resources.getSystem().displayMetrics.heightPixels / 5
                val height = it.height
                BottomSheetBehavior.from(it).peekHeight = height.coerceAtMost(maxHeight)
            }
        }

        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.listener = View.OnClickListener { showAddSourceDialog() }
        listener?.onFragmentReady()
    }

    private fun showAddSourceDialog() {
        val fragment = AddSourceDialogFragment.newInstance("")
        fragment.listener = object: AddSourceDialogFragment.Listener {
            override fun addSource(dataSource: DataSourcePresentationModel) {
                viewModel.getViewEvent().addSource(dataSource)
            }
        }
        fragment.show(childFragmentManager, null)
    }

    override fun onSetupViewModel() {
        super.onSetupViewModel()
        viewModel = ViewModelProvider(this,viewModelFactory).get(SourceSettingsViewModelImpl::class.java)
        viewModel.setInput(object: SourceSettingsViewModel.Input {
            override val initialSource: DataSourcePresentationModel? = this@SourceSettingsFragment.initialSource
        })
    }

    override fun observeResponse() {
        super.observeResponse()
        viewModel.getViewData().getLiveSources().observe(this, Observer {
            setupAdapter(it.first, it.second)
        })

        viewModel.getViewData().getAddedSource().observe(this, Observer {
            adapter.addData(it)
        })

        viewModel.getViewData().getRemovedSource().observe(this, Observer {
            adapter.removeData(it)
        })

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener?.onHide()
    }

    override fun onGetInputData() {
        super.onGetInputData()
        initialSource = arguments?.get(EXTRA_INITIAL_SOURCE) as DataSourcePresentationModel
    }

    private fun setupAdapter(list: List<DataSourcePresentationModel>, data: DataSourcePresentationModel?) {
        adapter = SourceAdapter(list.toMutableList(), data)
        adapter.listener = object: SourceAdapter.Listener {
            override fun onSourceClicked(model: DataSourcePresentationModel) {
                listener?.onSourceClicked(model)
                this@SourceSettingsFragment.dismiss()
            }

            override fun onDeleteClicked(model: DataSourcePresentationModel) {
                this@SourceSettingsFragment.showDeleteDialog(context!!, model)
            }
        }
        val layoutManager = LinearLayoutManager(context!!)
        rvMoreList.layoutManager = layoutManager
        rvMoreList.adapter = adapter
    }

    private fun showDeleteDialog(context: Context, model: DataSourcePresentationModel) {
        DialogUtils.showPositiveButtonDialog(context, getString(R.string.delete_source), getString(R.string.delete_source_description), getString(R.string.confirm), DialogInterface.OnClickListener { _, _ -> viewModel.getViewEvent().removeSource(model.id) })
    }

    interface Listener {
        fun onHide()
        fun onFragmentReady()
        fun onSourceClicked(source: DataSourcePresentationModel)
    }

    companion object {
        const val EXTRA_INITIAL_SOURCE = "EXTRA_INITIAL_SOURCE"

        fun newInstance(initialSource: DataSourcePresentationModel): SourceSettingsFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_INITIAL_SOURCE, initialSource)
            val fragment = SourceSettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
