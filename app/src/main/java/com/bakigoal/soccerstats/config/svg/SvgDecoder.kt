package com.bakigoal.soccerstats.config.svg

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.request.target.Target
import com.caverock.androidsvg.SVG
import java.io.InputStream

class SvgDecoder : ResourceDecoder<InputStream, SVG> {
    override fun handles(source: InputStream, options: Options): Boolean {
        return true
    }

    override fun decode(
        source: InputStream,
        width: Int,
        height: Int,
        options: Options
    ): Resource<SVG> {
        val svg = SVG.getFromInputStream(source)
        if (width != Target.SIZE_ORIGINAL) svg.documentWidth = width.toFloat()
        if (height != Target.SIZE_ORIGINAL) svg.documentHeight = height.toFloat()
        return SimpleResource(svg)
    }
}