package com.bakigoal.soccerstats.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakigoal.soccerstats.R
import com.bakigoal.soccerstats.databinding.FragmentDevByteBinding
import com.bakigoal.soccerstats.domain.Video
import com.bakigoal.soccerstats.ui.adapter.SoccerStats
import com.bakigoal.soccerstats.ui.viewmodel.SoccerStatsViewModel

/**
 * Show a list of DevBytes on screen.
 */
class SoccerStatsFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onViewCreated(), which we
     * do in this Fragment.
     */
    private val viewModel: SoccerStatsViewModel by lazy {
        val app = requireActivity().application
        ViewModelProvider(this, SoccerStatsViewModel.Factory(app))[SoccerStatsViewModel::class.java]
    }

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     */
    private lateinit var soccerStatsAdapter: SoccerStats

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param s If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val binding: FragmentDevByteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dev_byte, container, false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        soccerStatsAdapter = SoccerStats(SoccerStats.VideoClick { openVideo(it) })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = soccerStatsAdapter
        }

        return binding.root
    }

    /**
     * Called immediately after onCreateView() has returned, and fragment's
     * view hierarchy has been created.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, { videos ->
            videos?.apply {
                soccerStatsAdapter.videos = videos
            }
        })
    }

    private fun openVideo(it: Video) {
        // context is not around, we can safely discard this click since the Fragment is no
        // longer on the screen
        val packageManager = context?.packageManager ?: return

        // Try to generate a direct intent to the YouTube app
        var intent = Intent(Intent.ACTION_VIEW, it.youtubeLaunchUri)
        // if YouTube app isn't found, use the web url
        @SuppressLint("QueryPermissionsNeeded")
        if (intent.resolveActivity(packageManager) == null) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
        }

        startActivity(intent)
    }

    /**
     * Helper method to generate YouTube app links
     */
    private val Video.youtubeLaunchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }
}