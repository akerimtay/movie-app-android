package com.akerimtay.movieapp.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.akerimtay.movieapp.BuildConfig
import com.bumptech.glide.Glide

@BindingAdapter(value = ["loadW500Image", "placeholder"], requireAll = false)
fun ImageView.loadW500Image(path: String?, placeholder: Drawable?) {
    val url = BuildConfig.IMAGE_URL + "w500" + path
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

@BindingAdapter("android:visibility")
fun View.bindVisible(visible: Boolean) {
    isVisible = visible
}