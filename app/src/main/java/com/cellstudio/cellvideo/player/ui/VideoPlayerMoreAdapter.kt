package com.cellstudio.cellvideo.player.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemVideoPlayerMoreBinding
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerAction

class VideoPlayerMoreAdapter(private var models: List<CellPlayerAction>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createItemViewHolder(parent)
    }

    private fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVideoPlayerMoreBinding> (layoutInflater, LAYOUT_ITEM, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val model = models[position]
            holder.binding.model = model.text
            holder.binding.icon = model.icon
            holder.binding.listener = model.action
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    fun setData(models: List<CellPlayerAction>) {
        this.models = models
        notifyDataSetChanged()
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoPlayerMoreBinding

        constructor(view: View) : super(view)

        constructor(binding: ItemVideoPlayerMoreBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    companion object {
        @LayoutRes
        val LAYOUT_ITEM = R.layout.item_video_player_more
    }
}