package com.cellstudio.cellvideo.player.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemVideoPlayerSpeedBinding
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerPlaySpeed

class VideoPlayerSpeedAdapter(private var models: Array<CellPlayerPlaySpeed>, private var selected: CellPlayerPlaySpeed)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createItemViewHolder(parent)
    }

    private fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVideoPlayerSpeedBinding> (layoutInflater, LAYOUT_ITEM, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val model = models[position]
            holder.binding.model = model.text
            holder.binding.selected = model == selected
            holder.binding.setListener {
                listener?.onSpeedSelected(model)
            }
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoPlayerSpeedBinding

        constructor(view: View) : super(view)

        constructor(binding: ItemVideoPlayerSpeedBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    interface Listener {
        fun onSpeedSelected(speed: CellPlayerPlaySpeed)
    }

    companion object {
        @LayoutRes
        val LAYOUT_ITEM = R.layout.item_video_player_speed
    }
}