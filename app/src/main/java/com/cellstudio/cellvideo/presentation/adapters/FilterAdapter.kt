package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemGridImageBinding
import com.cellstudio.cellvideo.databinding.ItemTextBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel

class FilterAdapter(private var models: MutableList<LiveSourcePresentationModel>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: Listener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val layoutInflater = LayoutInflater.from(context)
//        val binding = DataBindingUtil.inflate<ItemTextBinding>(layoutInflater, R.layout.item_text, parent, false)
//        return ItemViewHolder(binding)
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemGridImageBinding>(layoutInflater, R.layout.item_grid_image, parent, false)
        return ItemImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding.model = models[position].name
            holder.binding.listener = View.OnClickListener { listener?.onModelClicked(models[position].url) }
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

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onModelClicked(url: String)
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
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
}
