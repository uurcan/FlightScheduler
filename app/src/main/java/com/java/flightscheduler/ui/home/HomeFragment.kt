package com.java.flightscheduler.ui.home

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentHomeBinding
import com.java.flightscheduler.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>(R.layout.fragment_home), View.OnClickListener{

    override fun init() {}

    override fun setViewModelFactory(): ViewModelProvider.Factory? = defaultViewModelProviderFactory

    override fun setViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onBind() {
        initializeViews()
        initializeResults()
    }

    private fun initializeResults() {
        viewModel?.getPlaces()?.observe(viewLifecycleOwner, {
            binding?.setVariable(BR.homeViewModel, viewModel)
        })
    }

    private fun initializeViews() {
        binding?.buttonFlightOffersMain?.setOnClickListener(this)
        binding?.buttonHotelOffersMain?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            binding?.buttonFlightOffersMain?.id -> findNavController().navigate(R.id.action_nav_home_to_nav_flight_search)
            binding?.buttonHotelOffersMain?.id -> findNavController().navigate(R.id.action_nav_home_to_nav_hotel_search)
        }
    }
}

