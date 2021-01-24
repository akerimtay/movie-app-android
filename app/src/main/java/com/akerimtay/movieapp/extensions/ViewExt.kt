package com.akerimtay.movieapp.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.akerimtay.movieapp.BuildConfig
import com.akerimtay.movieapp.utils.RequestDrawableListenerAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["loadW500Image", "placeholder"], requireAll = false)
fun ImageView.loadW500Image(path: String?, placeholder: Drawable?) {
    val url = path?.let { BuildConfig.IMAGE_URL + "w500" + path } ?: ""
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

@BindingAdapter(value = ["loadW185Image", "placeholder"], requireAll = false)
fun ImageView.loadW185Image(path: String?, placeholder: Drawable?) {
    val url = path?.let { BuildConfig.IMAGE_URL + "w185" + path } ?: ""
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadOriginalImage(
    path: String?,
    onFailed: (() -> Unit)? = null,
    onReady: (() -> Unit)? = null
) {
    val url = path?.let { BuildConfig.IMAGE_URL + "original" + path } ?: ""
    Glide.with(context)
        .load(url)
        .listener(RequestDrawableListenerAdapter(onFailed, onReady))
        .into(this)
}

@BindingAdapter(value = ["loadImage", "placeholder"], requireAll = false)
fun ImageView.loadImage(url: String?, placeholder: Drawable?) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

@BindingAdapter("android:visibility")
fun View.bindVisible(visible: Boolean) {
    isVisible = visible
}