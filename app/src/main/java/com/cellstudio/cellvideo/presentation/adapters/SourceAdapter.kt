package com.cellstudio.cellvideo.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.ItemSourceBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel

class SourceAdapter(private var models: MutableList<DataSourcePresentationModel>, private val initialSelection: DataSourcePresentationModel?)
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
            holder.binding.model = model.label
            holder.binding.leftIcon = if (initialSelection?.id == model.id) ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_check_white_24dp) else null
            holder.binding.rightIcon = if (model.isEditable) ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_delete_white_24dp) else null
            holder.binding.rightIconEnabled = model.id != initialSelection?.id
            if (model.id != initialSelection?.id) {
                holder.binding.rightIconListener = View.OnClickListener { listener?.onDeleteClicked(model) }
            }
            holder.binding.listener = View.OnClickListener {
                if (initialSelection?.id == model.id)
                    return@OnClickListener
                listener?.onSourceClicked(model)
            }
        }
    }

    fun addData(model: DataSourcePresentationModel) {
        models.add(model)
        notifyItemInserted(models.size - 1)
    }

    fun removeData(id: String) {
        val index = models.indexOfFirst { it.id == id}
        if (index > -1) {
            models.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    interface Listener {
        fun onSourceClicked(model: DataSourcePresentationModel)
        fun onDeleteClicked(model: DataSourcePresentationModel)
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