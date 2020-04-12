package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemGridImageBinding
import com.cellstudio.cellvideo.databinding.ItemLoadingBinding
import com.cellstudio.cellvideo.databinding.ItemTextBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel

class LiveSourceAdapter(private var models: MutableList<LiveSourcePresentationModel>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: Listener ?= null
    private var loading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TEXT -> createTextViewHolder(parent)
            else ->  createLoadingViewHolder(parent)
        }
//        val layoutInflater = LayoutInflater.from(context)
//        val binding = DataBindingUtil.inflate<ItemGridImageBinding>(layoutInflater, R.layout.item_grid_image, parent, false)
//        return ItemImageViewHolder(binding)
    }

    private fun createLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemLoadingBinding>(layoutInflater, R.layout.item_loading, parent, false)
        return ItemLoadingViewHolder(binding)
    }

    private fun createTextViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemTextBinding>(layoutInflater, R.layout.item_text, parent, false)
        return ItemTextViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(loading) models.size + 1 else models.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (loading && position == itemCount - 1) {
            ITEM_LOADING
        } else ITEM_TEXT
    }

    fun setLoading(loading: Boolean) {
        if (this.loading == loading)
            return
        this.loading = loading
        if (loading) {
            notifyItemInserted(itemCount -1)
        } else {
            notifyItemRemoved(itemCount)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemTextViewHolder) {
            val model = models[position]
            holder.binding.model = model.name
            holder.binding.imageUrl = model.image
            holder.binding.listener = View.OnClickListener { listener?.onModelClicked(model.url) }
        } else if (holder is ItemImageViewHolder) {
            val model = models[position]
            holder.binding.imageUrl = model.image
            holder.binding.name = model.name
//            holder.binding.ivLogo.setAspect(model.imageWidth.toFloat()/ model.imageHeight.toFloat())
            holder.binding.listener = View.OnClickListener { listener?.onModelClicked(model.url) }
        }
    }

    fun updateData(models: MutableList<LiveSourcePresentationModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun addDatas(models: List<LiveSourcePresentationModel>) {
        val index = this.models.size
        this.models.addAll(models)
        notifyItemRangeInserted(index, this.models.size -1)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun getLoading(): Boolean {
        return this.loading
    }

    interface Listener {
        fun onModelClicked(url: String)
    }

    class ItemTextViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemTextBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemTextBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    class ItemImageViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemGridImageBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemGridImageBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    class ItemLoadingViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemLoadingBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemLoadingBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    companion object {
        const val ITEM_LOADING = 1001
        const val ITEM_TEXT = 1002
    }
}
