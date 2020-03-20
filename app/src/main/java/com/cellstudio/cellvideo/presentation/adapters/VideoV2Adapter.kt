package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemVideoHeaderBinding
import com.cellstudio.cellvideo.databinding.ItemVideoV2Binding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListViewType
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.presentation.components.StickyHeaderItemDecoration
import com.cellstudio.cellvideo.presentation.utils.ZoomPageTransformer
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class VideoV2Adapter(private val items:MutableList<VideoListPresentationModel>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderItemDecoration.StickyHeaderInterface{
    private var listener: Listener?= null

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = -1
        var position = itemPosition
        do {
            if (this.isHeader(position)) {
                headerPosition = position
                break
            }
            position -= 1
        } while (position >= 0)
        return headerPosition
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return R.layout.item_video_header
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        val binding = DataBindingUtil.bind<ItemVideoHeaderBinding>(header)
        if (items.size > 0) {
            binding?.tvHeaderTitle?.text = items[headerPosition].title
        }
    }

    override fun isHeader(itemPosition: Int): Boolean {
        return if (items.size > 0 ) {
            items[itemPosition].viewType == VideoListViewType.VIEW_TYPE_HEADER
        } else {
            false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VideoListViewType.VIEW_TYPE_HEADER.type -> {
                val binding = DataBindingUtil.inflate<ItemVideoHeaderBinding>(LayoutInflater.from(context), R.layout.item_video_header, parent, false)
                 ItemHeaderViewHolder(binding)
            }
            VideoListViewType.VIEW_TYPE_RAIL.type -> {
                val binding = DataBindingUtil.inflate<ItemVideoV2Binding>(LayoutInflater.from(context), R.layout.item_video_v2, parent, false)
                ItemRailViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemVideoV2Binding>(LayoutInflater.from(context), R.layout.item_video_v2, parent, false)
                ItemViewHolder(binding)
            }
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val model = items[position]
            if (holder.adapter == null) {
                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val rvVideoMain = holder.binding.rvVideoMain
                holder.adapter = VideoBannerAdapter(model.videoList?.toMutableList()?: mutableListOf<VideoPresentationModel>(), context)
                rvVideoMain.layoutManager = layoutManager
                rvVideoMain.adapter = holder.adapter
                val snapHelper = GravitySnapHelper(Gravity.CENTER)
                snapHelper.attachToRecyclerView(rvVideoMain)
                snapHelper.maxFlingSizeFraction = 0.75f
                snapHelper.snapLastItem = true
                rvVideoMain.scrollToPosition(Int.MAX_VALUE / 2 - 1)
                rvVideoMain.smoothScrollBy(1, 0)
                rvVideoMain.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        (rvVideoMain as? ViewGroup)?.apply {
                            children.forEach { child ->
                                val childPosition = (child.left + child.right) / 2f
                                val center = width / 2

                                ZoomPageTransformer.transformPage(child, (center - childPosition) / width)
                            }
                        }
                    }
                })
            }
        } else if (holder is ItemRailViewHolder) {
            val model = items[position]
            if (holder.adapter == null) {
                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val rvVideoMain = holder.binding.rvVideoMain
                holder.adapter = VideoRailAdapter(model.videoList?.toMutableList()?: mutableListOf<VideoPresentationModel>(), context)
                holder.adapter?.setListener (object: VideoRailAdapter.Listener {
                    override fun onModelClicked(model: VideoPresentationModel) {
                        listener?.onModelClicked(model)
                    }
                })
                rvVideoMain.layoutManager = layoutManager
                rvVideoMain.adapter = holder.adapter
            }
        } else if (holder is ItemHeaderViewHolder) {
            val model = items[position]
            holder.binding.tvHeaderTitle.text = model.title
        }
    }

    override fun getItemCount(): Int { return items.size }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.type
    }

    fun addData(model: VideoListPresentationModel) {
        val index = items.size
        if (!model.title.isNullOrBlank()) {
            val title = VideoListPresentationModel.create(model.title)
            items.addAll(listOf(title, model))
            notifyItemRangeInserted(index, items.size)
        } else {
            items.add(model)
            notifyItemInserted(index)
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onModelClicked(model: VideoPresentationModel)
    }

    class ItemHeaderViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoHeaderBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemVideoHeaderBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoV2Binding
        var adapter: VideoBannerAdapter ?= null
        constructor(view: View) : super(view)
        constructor(binding: ItemVideoV2Binding) : this(binding.root) {
            this.binding = binding
        }
    }

    class ItemRailViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoV2Binding
        var adapter: VideoRailAdapter ?= null
        constructor(view: View) : super(view)
        constructor(binding: ItemVideoV2Binding) : this(binding.root) {
            this.binding = binding
        }
    }
}