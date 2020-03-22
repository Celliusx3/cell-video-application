package com.cellstudio.cellvideo.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.databinding.ItemSourceBinding

class SourceAdapter(private var models: Array<DataSource>, private val initialSelection: DataSource)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener ?= null

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
            holder.binding.model = model.text
            holder.binding.listener = View.OnClickListener { listener?.onSourceClicked(model) }
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    interface Listener {
        fun onSourceClicked(model: DataSource)
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