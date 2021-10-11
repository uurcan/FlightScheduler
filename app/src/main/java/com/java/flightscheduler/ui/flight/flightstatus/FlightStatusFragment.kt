package com.java.flightscheduler.ui.flight.flightstatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_flight_status.*

@AndroidEntryPoint
class FlightStatusFragment : Fragment() {
    private lateinit var flightStatusViewModel : FlightStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flight_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flightStatusViewModel = ViewModelProvider(this).get(FlightStatusViewModel::class.java)
        flightStatusViewModel.getFlightStatusLiveData()?.observe(viewLifecycleOwner, {
                flightStatus ->
            if (flightStatus != null)
                text_flight_status.text = flightStatus[0].flightDesignator?.carrierCode.toString()
        })
    }
}
