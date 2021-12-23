package com.bakigoal.soccerstats.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.decode.SvgDecoder
import coil.imageLoader
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

@BindingAdapter("goneIfNull")
fun goneIfNull(view: View, it: Any?) {
    view.visibility = if (it == null) View.GONE else View.VISIBLE
}

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {

    Glide.with(imageView.context).load(url).into(imageView)
}

/**
 * Binding adapter used to display SVG images from URL using Coil
 */
@BindingAdapter("imageSvgUrl")
fun ImageView.setImageSvgUrl(url: String) {
    val context = this.context

    val imageRequest = ImageRequest.Builder(context)
        .data(url)
        .target(this)
        .decoder(SvgDecoder(context))
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_broken_image)
        .crossfade(true)
        .crossfade(500)
        .build()

    context.imageLoader.enqueue(imageRequest)
}