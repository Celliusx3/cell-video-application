package com.cellstudio.cellvideo.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemSingleSelectionBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.SelectionModel

class SingleSelectionAdapter<T: SelectionModel>(private var models: List<T>, private var selected: T?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener<T> ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createItemViewHolder(parent)
    }

    private fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSingleSelectionBinding> (layoutInflater, LAYOUT_ITEM, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val model = models[position]
            holder.binding.model = model.getText()
            holder.binding.leftIcon = if (model == selected) ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_check_white_24dp) else null
            holder.binding.listener = View.OnClickListener { listener?.onSelectionClicked(model) }
        }
    }

    fun setData(models: List<T>) {
        this.models = models
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return models.size
    }

    interface Listener<T: SelectionModel> {
        fun onSelectionClicked(model: T)
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemSingleSelectionBinding

        constructor(view: View) : super(view)

        constructor(binding: ItemSingleSelectionBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    companion object {
        @LayoutRes
        val LAYOUT_ITEM = R.layout.item_single_selection
    }
}