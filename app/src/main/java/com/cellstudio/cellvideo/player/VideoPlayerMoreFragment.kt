package com.cellstudio.cellvideo.player

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.FragmentVideoPlayerMoreBinding
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerAction
import com.cellstudio.cellvideo.player.ui.VideoPlayerMoreAdapter
import com.cellstudio.cellvideo.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_video_player_more.*

class VideoPlayerMoreFragment : BaseBottomSheetDialogFragment() {
    private var binding: FragmentVideoPlayerMoreBinding? = null

    private lateinit var adapter: VideoPlayerMoreAdapter
    private lateinit var actions: List<CellPlayerAction>
    var listener: Listener?= null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_video_player_more
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

        listener?.onFragmentReady()
    }

    override fun setupUI() {
        super.setupUI()
        setupAdapter()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener?.onHide()
    }

    fun setActions(actions: List<CellPlayerAction>) {
        this.actions = actions
        adapter.setData(actions)
    }

    private fun setupAdapter() {
        adapter = VideoPlayerMoreAdapter(listOf())
        val layoutManager = LinearLayoutManager(context!!)
        rvMoreList.layoutManager = layoutManager
        rvMoreList.adapter = adapter
    }

    interface Listener {
        fun onHide()
        fun onFragmentReady()
    }

    companion object {
        fun newInstance(): VideoPlayerMoreFragment {
            val args = Bundle()
            val fragment = VideoPlayerMoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
