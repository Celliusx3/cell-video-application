package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemEpisodeBinding
import com.cellstudio.cellvideo.databinding.ItemLoadingBinding

class DetailsAdapter(private val items:List<Pair<String, String>>, private val context: Context, private var selectedPosition: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var listener: Listener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADING_TYPE -> {
                val binding = DataBindingUtil.inflate<ItemLoadingBinding>(LayoutInflater.from(context), R.layout.item_loading, parent, false)
                SearchAdapter.ItemLoadingHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemEpisodeBinding>(LayoutInflater.from(context), R.layout.item_episode, parent, false)
                ItemViewHolder(binding)
            }
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val model = items[position]
            holder.binding.text = model.first
            holder.binding.selected = selectedPosition == position
            holder.binding.listener = View.OnClickListener {
                if (selectedPosition == position)
                    return@OnClickListener
                listener?.onModelClicked(model.first, model.second)
                if (selectedPosition > -1) {
                    notifyItemChanged(selectedPosition)
                }
                this.selectedPosition = position
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onModelClicked(title: String, url: String)
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemEpisodeBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemEpisodeBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    companion object {
        const val LOADING_TYPE = 101
        const val ITEM_TYPE = 102
    }
}