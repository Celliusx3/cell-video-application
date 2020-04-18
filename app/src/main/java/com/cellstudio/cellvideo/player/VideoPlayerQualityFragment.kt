package com.cellstudio.cellvideo.player

import android.view.View
import com.cellstudio.cellvideo.player.cellplayer.models.QualityLevel
import com.cellstudio.cellvideo.presentation.screens.SingleSelectionBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_single_selection.*

class VideoPlayerQualityFragment : SingleSelectionBottomSheetFragment<QualityLevel>() {
    override fun setupUI() {
        super.setupUI()
        etSearchInput.visibility = View.GONE
    }
}
