package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemLoadingBinding
import com.cellstudio.cellvideo.databinding.ItemVideoBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel

class SearchAdapter(private val items:MutableList<VideoPresentationModel>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    protected var loadingData = false
    private var listener: Listener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADING_TYPE -> {
                val binding = DataBindingUtil.inflate<ItemLoadingBinding>(LayoutInflater.from(context), R.layout.item_loading, parent, false)
                ItemLoadingHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemVideoBinding>(LayoutInflater.from(context), R.layout.item_video, parent, false)
                ItemViewHolder(binding)
            }
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val model = items[position]
            holder.binding.model = model
            holder.binding.listener = View.OnClickListener { listener?.onModelClicked(model) }
        }
    }

    override fun getItemCount(): Int {
        return if (loadingData) items.size + 1
        else items.size
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE
    }

    fun addData(models: List<VideoPresentationModel>) {
        val index = items.size
        items.addAll(models)
        notifyItemRangeInserted(index, index + models.size - 1)

    }

    fun setLoading(loading: Boolean) {
        if (!loadingData && loading) notifyItemInserted(items.size)
        else if (loadingData && !loading) notifyItemRemoved(items.size)

        this.loadingData = loading
    }

    fun setListener(listener: Listener) {
        this.listener = listener

    }

    interface Listener {
        fun onModelClicked(model: VideoPresentationModel)
    }

    class ItemLoadingHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemLoadingBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemLoadingBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemVideoBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    companion object {
        const val LOADING_TYPE = 101
        const val ITEM_TYPE = 102
    }
}