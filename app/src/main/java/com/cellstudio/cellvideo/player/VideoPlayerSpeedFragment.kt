package com.cellstudio.cellvideo.player

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.FragmentVideoPlayerSpeedBinding
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerPlaySpeed
import com.cellstudio.cellvideo.player.ui.VideoPlayerSpeedAdapter
import com.cellstudio.cellvideo.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_video_player_speed.*


class VideoPlayerSpeedFragment : BaseBottomSheetDialogFragment() {
    private var binding: FragmentVideoPlayerSpeedBinding? = null

    private lateinit var adapter: VideoPlayerSpeedAdapter
    var listener: Listener?= null

    private lateinit var speed: CellPlayerPlaySpeed

    override fun getLayoutResource(): Int {
        return R.layout.fragment_video_player_speed
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheetInternal?.let {
                val maxHeight = Resources.getSystem().displayMetrics.heightPixels
                val height = it.height
                BottomSheetBehavior.from(it).peekHeight = height.coerceAtMost(maxHeight)
            }
        }



        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
    }

    override fun setupUI() {
        super.setupUI()
        setupAdapter()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener?.onHide()
    }

    override fun onGetInputData() {
        super.onGetInputData()
        arguments?.let {
            speed = it.getSerializable(EXTRA_PLAYBACK_SPEED) as CellPlayerPlaySpeed
        }
    }

    private fun setupAdapter() {
        adapter = VideoPlayerSpeedAdapter(CellPlayerPlaySpeed.values(), speed)
        adapter.listener = object: VideoPlayerSpeedAdapter.Listener{
            override fun onSpeedSelected(speed: CellPlayerPlaySpeed) {
                listener?.onSpeedSelected(speed)
                listener?.onHide()
                this@VideoPlayerSpeedFragment.dismiss()
            }
        }
        val layoutManager = LinearLayoutManager(context!!)
        rvSpeedList.layoutManager = layoutManager
        rvSpeedList.adapter = adapter
    }

    interface Listener {
        fun onSpeedSelected(speed: CellPlayerPlaySpeed)
        fun onHide()
    }

//    fun onBackPressed() {
//        if (childFragmentManager.backStackEntryCount > 1) {
//            childFragmentManager.popBackStack()
//        } else {
//            dismiss()
//        }
//    }

    companion object {
        const val EXTRA_PLAYBACK_SPEED = "EXTRA_PLAYBACK_SPEED"

        fun newInstance(speed: CellPlayerPlaySpeed): VideoPlayerSpeedFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_PLAYBACK_SPEED, speed)
            val fragment = VideoPlayerSpeedFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
