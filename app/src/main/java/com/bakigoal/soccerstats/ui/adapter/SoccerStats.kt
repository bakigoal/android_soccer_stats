package com.bakigoal.soccerstats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.DevbyteItemBinding
import com.bakigoal.soccerstats.domain.Video

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class SoccerStats(private val callback: VideoClick) : RecyclerView.Adapter<SoccerStats.DevByteViewHolder>() {

    class VideoClick(val apply: (Video) -> Unit) {
        fun onClick(video: Video) = apply(video)
    }

    class DevByteViewHolder(val viewDataBinding: DevbyteItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.devbyte_item
        }
    }

    /**
     * The videos that our Adapter will show
     */
    var videos: List<Video> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = videos.size

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val itemBinding: DevbyteItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DevByteViewHolder.LAYOUT,
            parent,
            false
        )
        return DevByteViewHolder(itemBinding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.video = videos[position]
            it.videoCallback = callback
        }
    }

}