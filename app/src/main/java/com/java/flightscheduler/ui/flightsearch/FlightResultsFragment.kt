package com.java.flightscheduler.ui.flightsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_flight_list.*

@AndroidEntryPoint
class FlightResultsFragment : Fragment() {
    private val flightSearchViewModel: FlightSearchViewModel by activityViewModels()
    private lateinit var flightSearchAdapter : FlightSearchAdapter
    private var flightSearch : FlightSearch? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flight_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        rv_flight_list.layoutManager = layoutManager
        rv_flight_list.setHasFixedSize(true)
        flightSearch = flightSearchViewModel.getFlightSearchLiveData()?.value
        flightSearch?.let {
            flightSearchViewModel.getFlightData(it)?.observe(viewLifecycleOwner, { flightData ->
                flightSearchAdapter = FlightSearchAdapter(flightData)
                rv_flight_list.adapter = flightSearchAdapter
            })
        }
    }
}
