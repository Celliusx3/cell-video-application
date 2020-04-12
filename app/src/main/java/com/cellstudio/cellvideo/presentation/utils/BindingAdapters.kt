package com.cellstudio.cellvideo.presentation.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
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

    @BindingAdapter("src_rawImage")
    @JvmStatic fun setImageUriRaw(view: ImageView, imageUri: String) {
        Glide.with(view.context)
            .load(imageUri)
            .placeholder(R.drawable.ic_play)
            .error(R.drawable.ic_play)
            .override(Target.SIZE_ORIGINAL)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @BindingAdapter("setBold")
    @JvmStatic fun setBold(textView: TextView, isBold: Boolean) {
        TextViewCompat.setTextAppearance(textView,
            if (isBold)R.style.AppTheme_TextView_Bold else R.style.AppTheme_TextView_Regular)
    }

}