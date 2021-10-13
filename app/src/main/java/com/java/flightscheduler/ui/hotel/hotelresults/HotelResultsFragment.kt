package com.java.flightscheduler.ui.hotel.hotelresults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.databinding.FragmentHotelResultsBinding
import com.java.flightscheduler.ui.base.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelResultsFragment : Fragment(),HotelResultsAdapter.HotelResultsListener{
    private val arguments by navArgs<HotelResultsFragmentArgs>()
    private lateinit var binding : FragmentHotelResultsBinding
    private val hotelResultsViewModel : HotelResultsViewModel by viewModels()
    private lateinit var hotelSearchAdapter : HotelResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_list,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeHotelHeader()
        initializeHotelResults()
    }

    private fun initializeHotelResults() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFlightList.layoutManager = layoutManager
        binding.rvFlightList.setHasFixedSize(true)

        hotelResultsViewModel.getHotelData(arguments.hotelSearch)?.observeOnce { hotelData ->
            if (hotelData.isEmpty()) {
                binding.txtHotelSearchErrorMessage.visibility = View.VISIBLE
                binding.txtHotelSearchErrorMessage.text =
                    getString(R.string.text_no_flight_found)
            }
            hotelSearchAdapter = HotelResultsAdapter(hotelData,requireContext(),this)
            binding.rvFlightList.adapter = hotelSearchAdapter
        }
        hotelResultsViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.pbHotelSearch.visibility = if (it) View.VISIBLE else View.GONE
        }

        hotelResultsViewModel.errorLiveData?.observeOnce {
            binding.txtHotelSearchErrorMessage.visibility = View.VISIBLE
            binding.txtHotelSearchErrorMessage.text = it
        }
    }

    private fun initializeHotelHeader() {
        binding.setVariable(BR.hotelSearch,arguments.hotelSearch)
        binding.executePendingBindings()
    }

    override fun onItemClick(view: View, item: HotelOffer) {
        val action = HotelResultsFragmentDirections.actionNavHotelResultsToHotelDetailsFragment(item)
        findNavController().navigate(action)
    }
}

