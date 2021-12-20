package com.bakigoal.soccerstats.ui.fragment

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
import com.bakigoal.soccerstats.domain.Country
import com.bakigoal.soccerstats.ui.adapter.SoccerStatsAdapter
import com.bakigoal.soccerstats.ui.viewmodel.SoccerStatsViewModel
import com.google.android.material.snackbar.Snackbar

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
    private lateinit var soccerStatsAdapterAdapter: SoccerStatsAdapter

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

        soccerStatsAdapterAdapter =
            SoccerStatsAdapter(SoccerStatsAdapter.CountryClick { countryClicked(it) })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = soccerStatsAdapterAdapter
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
        viewModel.countries.observe(viewLifecycleOwner, { videos ->
            videos?.apply {
                soccerStatsAdapterAdapter.countries = videos
            }
        })

        viewModel.showError.observe(viewLifecycleOwner, { errorText ->
            if (errorText.isNotEmpty()) {
                showSnackbarWithText("Error: $errorText")
                viewModel.doneShowError()
            }
        })
    }

    private fun countryClicked(country: Country) {
        showSnackbarWithText("Selected: ${country.name}")
    }

    private fun showSnackbarWithText(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }
}