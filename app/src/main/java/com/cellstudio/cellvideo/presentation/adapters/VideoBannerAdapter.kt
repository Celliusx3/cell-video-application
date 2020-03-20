package com.cellstudio.cellvideo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemVideoBannerBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import io.reactivex.subjects.PublishSubject

class VideoBannerAdapter(private var models: MutableList<VideoPresentationModel>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val selectedModel = PublishSubject.create<VideoPresentationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemVideoBannerBinding>(layoutInflater, R.layout.item_video_banner, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (models.isEmpty()) 0 else Integer.MAX_VALUE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding.model = models[position % models.size]
        }
    }

    fun updateData(models: MutableList<VideoPresentationModel>) {
        this.models = models
        notifyDataSetChanged()
//        snapHelper.scrollToPosition(itemCount/2)
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        lateinit var binding: ItemVideoBannerBinding
        constructor(view: View) : super(view)
        constructor(binding: ItemVideoBannerBinding) : this(binding.root) {
            this.binding = binding
        }
    }
}
