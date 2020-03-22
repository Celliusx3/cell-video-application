package com.cellstudio.cellvideo.presentation.screens.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.databinding.FragmentSettingsBinding
import com.cellstudio.cellvideo.interactor.viewmodel.settings.SettingsViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.settings.SettingsViewModelImpl
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment
import com.cellstudio.cellvideo.presentation.screens.splash.SplashActivity

class SettingsFragment : BaseInjectorFragment() {
    private lateinit var settingsViewModel: SettingsViewModel

    override fun getLayoutResource(): Int {
        return R.layout.fragment_settings
    }

    companion object {
        val TAG = SettingsFragment::class.java.simpleName

        fun newInstance(): SettingsFragment {
            val bundle = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        settingsViewModel = ViewModelProvider(this,viewModelFactory).get(SettingsViewModelImpl::class.java)

        val binding = DataBindingUtil.bind<FragmentSettingsBinding>(view)
        binding?.viewmodel = settingsViewModel
        binding?.lifecycleOwner = this

        settingsViewModel.getViewEvent().startScreen()

        settingsViewModel.getViewData().getOpenSourceSelectionDialog().observe(this, Observer {
            val fragment = SourceSettingsFragment.newInstance(it)
            fragment.listener = object: SourceSettingsFragment.Listener {
                override fun onFragmentReady() {}
                override fun onHide() {}
                override fun onSourceClicked(source: DataSource) {settingsViewModel.getViewEvent().updateSource(source)}
            }
            fragment.show(childFragmentManager, null)
        })

        settingsViewModel.getOutput().getResetData().observe(this, Observer {
            val i = Intent(context!!, SplashActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context!!.startActivity(i)
            Runtime.getRuntime().exit(0)
        })

    }
}