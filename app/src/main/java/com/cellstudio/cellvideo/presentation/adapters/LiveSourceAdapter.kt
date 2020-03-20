package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemTextBinding

class LiveSourceAdapter(private var models: MutableList<Pair<String, String>>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: Listener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemTextBinding>(layoutInflater, R.layout.item_text, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding.model = models[position].first
            holder.binding.listener = View.OnClickListener { listener?.onModelClicked(models[position].second) }
        }
    }

    fun updateData(models: MutableList<Pair<String, String>>) {
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
}
