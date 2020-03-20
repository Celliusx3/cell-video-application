//package com.cellstudio.cellvideo.presentation.adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.cellstudio.cellvideo.R
//import com.cellstudio.cellvideo.databinding.ItemLoadingBinding
//import com.cellstudio.cellvideo.databinding.ItemVideoBinding
//import com.cellstudio.cellvideo.databinding.ItemVideoHeaderBinding
//import com.cellstudio.cellvideo.databinding.ItemVideoListBinding
//import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
//import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListPresentationModel
//import com.cellstudio.cellvideo.interactor.model.presentationmodel.ViewTypeModel
//import com.cellstudio.cellvideo.presentation.components.StickyHeaderItemDecoration
//
//class VideoListsAdapter(private val items:MutableList<VideoListPresentationModel>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderItemDecoration.StickyHeaderInterface{
//    override fun getHeaderPositionForItem(itemPosition: Int): Int {
//        var headerPosition = 0
//        var position = itemPosition
//        do {
//            if (this.isHeader(position)) {
//                headerPosition = position
//                break
//            }
//            position -= 1
//        } while (position >= 0)
//        return headerPosition    }
//
//    override fun getHeaderLayout(headerPosition: Int): Int {
//        return R.layout.item_video_header
//    }
//
//    override fun bindHeaderData(header: View, headerPosition: Int) {
//        if (items.size > 0 ) {
//            val binding = DataBindingUtil.bind<ItemVideoHeaderBinding>(header)
//            binding?.tvHeaderTitle?.text = items[headerPosition].title
//        }
//    }
//
//    override fun isHeader(itemPosition: Int): Boolean {
//        return true
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            ViewTypeModel.VIEW_TYPE_LOADING.type -> {
//                val binding = DataBindingUtil.inflate<ItemLoadingBinding>(LayoutInflater.from(context), R.layout.item_loading, parent, false)
//                VideoAdapter.ItemLoadingHolder(binding)
//            }
//            else -> {
//                val binding = DataBindingUtil.inflate<ItemVideoListBinding>(LayoutInflater.from(context), R.layout.item_video, parent, false)
//                VideoAdapter.ItemViewHolder(binding)
//            }
//        }
//    }
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is ItemViewHolder) {
//            val model = items[position]
//            holder.binding.model = model
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (!loadingData) VIEW_TYPE_ITEM
//        else {
//            if (position == items.size) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
//        }
//    }
//
//    fun addData(models: List<VideoListPresentationModel>) {
//        val index = items.size
//        items.addAll(models)
//        notifyItemRangeInserted(index, index + models.size - 1)
//
//    }
//
//    fun setLoading(loading: Boolean) {
//        if (!loadingData && loading) notifyItemInserted(items.size)
//        else if (loadingData && !loading) notifyItemRemoved(items.size)
//
//        this.loadingData = loading
//    }
//
//    class ItemLoadingHolder: RecyclerView.ViewHolder {
//        lateinit var binding: ItemLoadingBinding
//        constructor(view: View) : super(view)
//        constructor(binding: ItemLoadingBinding) : this(binding.root) {
//            this.binding = binding
//        }
//    }
//
//    class ItemViewHolder: RecyclerView.ViewHolder {
//        lateinit var binding: ItemVideoListBinding
//        constructor(view: View) : super(view)
//        constructor(binding: ItemVideoListBinding) : this(binding.root) {
//            this.binding = binding
//        }
//    }
//}