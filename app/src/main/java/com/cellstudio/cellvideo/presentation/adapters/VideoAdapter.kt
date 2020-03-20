//package com.cellstudio.cellvideo.presentation.adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.cellstudio.cellvideo.R
//import com.cellstudio.cellvideo.databinding.ItemLoadingBinding
//import com.cellstudio.cellvideo.databinding.ItemVideoBinding
//import com.cellstudio.cellvideo.databinding.ItemVideoHeaderBinding
//import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
//import com.cellstudio.cellvideo.interactor.model.presentationmodel.ViewTypeModel
//import com.cellstudio.cellvideo.presentation.components.StickyHeaderItemDecoration
//
//class VideoAdapter(private val items:MutableList<VideoPresentationModel>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderItemDecoration.StickyHeaderInterface{
//    override fun getHeaderPositionForItem(itemPosition: Int): Int {
//        var headerPosition = -1
//        var position = itemPosition
//        do {
//            if (this.isHeader(position)) {
//                headerPosition = position
//                break
//            }
//            position -= 1
//        } while (position >= 0)
//        return headerPosition
//    }
//
//    override fun getHeaderLayout(headerPosition: Int): Int {
//        return R.layout.item_video_header
//    }
//
//    override fun bindHeaderData(header: View, headerPosition: Int) {
//        val binding = DataBindingUtil.bind<ItemVideoHeaderBinding>(header)
//        if (items.size > 0) {
//            binding?.tvHeaderTitle?.text = items[headerPosition].name
//        }
//    }
//
//    override fun isHeader(itemPosition: Int): Boolean {
//        return if (items.size > 0 ) {
//            items[itemPosition].viewType == ViewTypeModel.VIEW_TYPE_TITLE
//        } else {
//            false
//        }
//    }
//
//    protected var loadingData = false
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            ViewTypeModel.VIEW_TYPE_LOADING.type -> {
//                val binding = DataBindingUtil.inflate<ItemLoadingBinding>(LayoutInflater.from(context), R.layout.item_loading, parent, false)
//                ItemLoadingHolder(binding)
//            }
//            ViewTypeModel.VIEW_TYPE_TITLE.type -> {
//                val binding = DataBindingUtil.inflate<ItemVideoHeaderBinding>(LayoutInflater.from(context), R.layout.item_video_header, parent, false)
//                ItemHeaderViewHolder(binding)
//            }
//            else -> {
//                val binding = DataBindingUtil.inflate<ItemVideoBinding>(LayoutInflater.from(context), R.layout.item_video, parent, false)
//                ItemViewHolder(binding)
//            }
//        }
//    }
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is ItemViewHolder) {
//            val model = items[position]
//            holder.binding.model = model
//        } else if (holder is ItemHeaderViewHolder) {
//            holder.binding.tvHeaderTitle.text = items[position].name
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return if (loadingData) items.size + 1
//        else items.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (position == items.size) ViewTypeModel.VIEW_TYPE_LOADING.type else items[position].viewType.type
////        return if (!loadingData) ViewTypeModel.VIEW_TYPE_LOADING.type
////        else {
////            if (position == items.size) ViewTypeModel.VIEW_TYPE_LOADING.type else items[position].viewType.type
////        }
//    }
//
//    fun addData(models: List<VideoPresentationModel>) {
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
//    class ItemHeaderViewHolder: RecyclerView.ViewHolder {
//        lateinit var binding: ItemVideoHeaderBinding
//        constructor(view: View) : super(view)
//        constructor(binding: ItemVideoHeaderBinding) : this(binding.root) {
//            this.binding = binding
//        }
//    }
//
//    class ItemViewHolder: RecyclerView.ViewHolder {
//        lateinit var binding: ItemVideoBinding
//        constructor(view: View) : super(view)
//        constructor(binding: ItemVideoBinding) : this(binding.root) {
//            this.binding = binding
//        }
//    }
//}