package com.java.flightscheduler.ui.delayprediction.predictionresults

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentDelayPredictionBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.utils.extension.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DelayPredictionFragment : BaseFragment<DelayPredictionViewModel, FragmentDelayPredictionBinding>(
    R.layout.fragment_delay_prediction
) {
    override val viewModel : DelayPredictionViewModel by viewModels()
    private val args by navArgs<DelayPredictionFragmentArgs>()

    override fun onBind() {
        viewModel.getPredictionData(args.predictionSearch)?.observeOnce{
            viewModel.setDelayPrediction(it)
        }
        binding?.predictionViewModel = viewModel
    }
}