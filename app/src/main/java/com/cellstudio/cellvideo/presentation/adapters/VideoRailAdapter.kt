package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemVideoRailBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class VideoRailAdapter(private var models: MutableList<VideoPresentationModel>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val selectedModel = PublishSubject.create<VideoPresentationModel>()
    private var listener: Listener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemVideoRailBinding>(layoutInflater, R.layout.item_video_rail, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding.model = models[position]
            holder.binding.listener = View.OnClickListener { listener?.onModelClicked(models[position]) }
        }
    }

    fun updateData(models: MutableList<VideoPresentationModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun getSelectedVideoPresentationModel(): Observable<VideoPresentationModel> {
        return selectedModel
    }

    fun setListener(listener: Listener) {
        this.listener = listener

    }

    interface Listener {
        fun onModelClicked(model: VideoPresentationModel)
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoRailBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemVideoRailBinding) : this(binding.root) {
            this.binding = binding
        }
    }
}
