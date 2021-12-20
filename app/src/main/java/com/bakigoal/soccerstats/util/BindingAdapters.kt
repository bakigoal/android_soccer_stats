package com.bakigoal.soccerstats.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bakigoal.soccerstats.R
import com.bumptech.glide.Glide


/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {

    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_broken_image)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}