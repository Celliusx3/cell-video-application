package com.cellstudio.cellvideo.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.cellstudio.cellvideo.R

object BindingAdapters {
    @BindingAdapter("android:src")
    @JvmStatic fun setImageUri(view: ImageView, imageUri: String) {
        Glide.with(view.context)
            .load(imageUri)
            .placeholder(R.drawable.ic_play)
            .error(R.drawable.ic_play)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

}