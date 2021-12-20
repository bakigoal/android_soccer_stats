package com.bakigoal.soccerstats.util

import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bakigoal.soccerstats.config.svg.GlideApp
import com.bakigoal.soccerstats.config.svg.SvgSoftwareLayerSetter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


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

    GlideApp.with(imageView.context)
        .`as`(PictureDrawable::class.java)
        .transition(withCrossFade())
        .listener(SvgSoftwareLayerSetter())
        .load(url).into(imageView)
}