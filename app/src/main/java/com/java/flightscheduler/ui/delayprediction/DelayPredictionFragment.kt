package com.java.flightscheduler.ui.delayprediction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_delay_prediction.*

@AndroidEntryPoint
class DelayPredictionFragment : Fragment() {
    private lateinit var predictionViewModel: DelayPredictionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delay_prediction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        predictionViewModel = ViewModelProvider(this).get(DelayPredictionViewModel::class.java)
        predictionViewModel.getPredictionData()?.observe(viewLifecycleOwner, {
            predictionData ->
                if (predictionData != null) {
                    text_delay_prediction.text = predictionData[1].probability.toString()
                }
        })
    }
}