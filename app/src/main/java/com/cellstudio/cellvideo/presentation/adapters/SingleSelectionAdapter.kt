package com.cellstudio.cellvideo.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemSourceBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.SelectionModel

class SingleSelectionAdapter<T: SelectionModel>(private var models: List<T>, private val initialSelection: T?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener<T> ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createItemViewHolder(parent)
    }

    private fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSourceBinding> (layoutInflater, LAYOUT_ITEM, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val model = models[position]
            holder.binding.model = model.getText()
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
        lateinit var binding: ItemSourceBinding

        constructor(view: View) : super(view)

        constructor(binding: ItemSourceBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    companion object {
        @LayoutRes
        val LAYOUT_ITEM = R.layout.item_source
    }
}