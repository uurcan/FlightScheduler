package com.java.flightscheduler.ui.itinerarymetrics.metricsresult

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.ItineraryMetricsBinding
import com.java.flightscheduler.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_itinerary_metrics.*

@AndroidEntryPoint
class ItineraryMetricsFragment : BaseFragment<ItineraryMetricsViewModel, ItineraryMetricsBinding>(R.layout.fragment_itinerary_metrics) {
    override val viewModel: ItineraryMetricsViewModel by viewModels()
    private val args by navArgs<ItineraryMetricsFragmentArgs>()

    override fun onBind() {
        viewModel.getMetricsData(args.metricSearch)?.observe(viewLifecycleOwner) { metricsData ->
            if (metricsData != null) {
                text_itinerary.text = metricsData[0].toString()
            }
        }
    }
}