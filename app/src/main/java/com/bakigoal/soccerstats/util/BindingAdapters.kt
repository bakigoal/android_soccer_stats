package com.bakigoal.soccerstats.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.request.ImageRequest
import com.bakigoal.soccerstats.R


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

@BindingAdapter("goneIfNotEmpty")
fun goneIfNotEmpty(view: View, it: List<Any>?) {
    view.visibility = if (it != null && it.isNotEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("goneIfEmpty")
fun goneIfEmpty(view: View, it: List<Any>?) {
    view.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
}

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        val context = imageView.context

        val imageRequestBuilder = ImageRequest.Builder(context)
            .data(url)
            .target(imageView)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .crossfade(true)
            .crossfade(500)

        if(url.endsWith("svg")){
            imageRequestBuilder.decoder(SvgDecoder(context))
        }

        context.imageLoader.enqueue(imageRequestBuilder.build())
    }
}

@BindingAdapter("changeRankColors")
fun TextView.changeRankColors(descriptionColor: String?) {
    descriptionColor?.apply {
        val color = Color.parseColor(descriptionColor)
        setBackgroundResource(R.drawable.rounder_corner_5)
        val gradientDrawable = background as GradientDrawable
        gradientDrawable.setColor(color)
        if (isColorDark(color)) setTextColor(Color.WHITE)
    }
}

fun isColorDark(color: Int): Boolean {
    val darkness =
        1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness >= 0.5
}
