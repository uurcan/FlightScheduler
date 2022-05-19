package com.java.flightscheduler.ui.hotel.hotelresults

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.databinding.FragmentHotelResultsBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.utils.extension.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelResultsFragment : BaseFragment<HotelResultsViewModel, FragmentHotelResultsBinding>(R.layout.fragment_hotel_list) {
    private val arguments by navArgs<HotelResultsFragmentArgs>()
    private lateinit var hotelSearchAdapter: HotelResultsAdapter
    override val viewModel: HotelResultsViewModel? by viewModels()

    override fun onBind() {
        initializeHotelHeader()
        initializeHotelResults()
    }

    private fun initializeHotelResults() {
        binding?.rvFlightList?.setHasFixedSize(true)
        viewModel?.getHotelData(arguments.hotelSearch)?.observeOnce { hotelData ->
            if (hotelData.isNullOrEmpty()) {
                binding?.txtHotelSearchErrorMessage?.visibility = View.VISIBLE
                binding?.txtHotelSearchErrorMessage?.text = getString(R.string.text_no_flight_found)
            }
            hotelSearchAdapter = HotelResultsAdapter({ switchToDetails(it) }, requireContext())
            hotelSearchAdapter.submitList(hotelData)
            binding?.rvFlightList?.adapter = hotelSearchAdapter
        }
    }

    private fun switchToDetails(item: HotelOffer) {
        val action = HotelResultsFragmentDirections.actionNavHotelResultsToHotelDetailsFragment(item)
        findNavController().navigate(action)
    }

    private fun initializeHotelHeader() {
        binding?.setVariable(BR.hotelSearch, arguments.hotelSearch)
        binding?.executePendingBindings()
    }
}
