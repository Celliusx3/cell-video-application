package com.cellstudio.cellvideo.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cellstudio.cellvideo.interactor.model.presentationmodel.PagePresentationModel
import com.cellstudio.cellvideo.presentation.screens.main.PageFragment
import com.cellstudio.cellvideo.presentation.screens.main.TextPageFragment
import java.util.*

class MainPagerAdapter(fragmentManager: FragmentManager,
                       private val fragmentPages: List<PagePresentationModel>) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentPageIds = ArrayList<Int>()

    private var homeFragment: PageFragment? = null
    private var settingsFragment: TextPageFragment? = null

    init {
        fragmentPageIds.clear()
        for (page in fragmentPages) {
            fragmentPageIds.add(page.id)
        }
    }

    override fun getItem(position: Int): Fragment {
        val fragmentPage = fragmentPages[position]
        if (settingsFragment == null) {
            settingsFragment = TextPageFragment.newInstance()
        }
        return settingsFragment as TextPageFragment

//        when (fragmentPage) {
//            Page.HomePage -> {
//                if (homeFragment == null) {
//                    homeFragment = HomeFragment.newInstance()
//                }
//                return homeFragment as HomeFragment
//            }
//            Page.SettingsPage -> {
//                if (settingsFragment == null) {
//                    settingsFragment = SettingsFragment.newInstance()
//                }
//                return settingsFragment as SettingsFragment
//            }
//            else -> {
//                if (homeFragment == null) {
//                    homeFragment = HomeFragment.newInstance()
//                }
//                return homeFragment as HomeFragment
//            }
//        }
    }

    override fun getCount(): Int {
        return fragmentPages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentPages[position].name
    }

    fun getPagePositionById(pageId: Int): Int {
        return fragmentPageIds.indexOf(pageId)
    }
}