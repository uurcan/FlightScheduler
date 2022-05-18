package com.java.flightscheduler.ui.itinerarymetrics.metricsresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.java.flightscheduler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_itinerary_metrics.*

@AndroidEntryPoint
class ItineraryMetricsFragment : Fragment() {
    private lateinit var metricsViewModel: ItineraryMetricsViewModel
    private val args by navArgs<ItineraryMetricsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_itinerary_metrics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        metricsViewModel = ViewModelProvider(this)[ItineraryMetricsViewModel::class.java]
        metricsViewModel.getMetricsData(args.metricSearch)?.observe(viewLifecycleOwner) { metricsData ->
            if (metricsData != null) {
                text_itinerary.text = metricsData[0].priceMetrics?.get(2)?.amount.toString()
            }
        }
    }
}