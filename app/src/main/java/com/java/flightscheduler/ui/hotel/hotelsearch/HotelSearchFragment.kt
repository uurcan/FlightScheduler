package com.java.flightscheduler.ui.hotel.hotelsearch

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.databinding.FragmentHotelSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HotelSearchFragment : Fragment(),View.OnClickListener {
    private val hotelSearchViewModel: HotelSearchViewModel by viewModels()
    private lateinit var binding : FragmentHotelSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hotel_search,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeCityDropdown()
    }

    private fun initializeViews() {
        binding.imgHotelAuditDecrease.setOnClickListener(this)
        binding.imgHotelAuditIncrease.setOnClickListener(this)
        binding.imgHotelRoomsDecrease.setOnClickListener(this)
        binding.imgHotelRoomsIncrease.setOnClickListener(this)
        binding.layoutHotelLanguage.setOnClickListener(this)
        binding.layoutHotelSort.setOnClickListener(this)
    }

    private fun initializeCityDropdown() {
        hotelSearchViewModel.getCities()?.observe(viewLifecycleOwner,
            {
                val adapter = HotelSearchAdapter(requireContext(), it.toTypedArray())
                binding.edtHotelSearch.setAdapter(adapter)
            }
        )
        binding.edtHotelSearch.setOnItemClickListener{ adapterView, _, i, _ ->
            val city = adapterView.getItemAtPosition(i)
            if (city is City) {
                binding.edtHotelSearch.setText(city.name)
            }
        }
    }

    private fun decreaseAuditCount() {
        val previousCount : Int = binding.txtHotelAuditCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.decreaseAuditCount(previousCount)
        binding.txtHotelAuditCount.text = currentCount.toString()
    }

    private fun increaseAuditCount() {
        val previousCount : Int = binding.txtHotelAuditCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.increaseAuditCount(previousCount)
        binding.txtHotelAuditCount.text = currentCount.toString()
    }

    private fun decreaseRoomCount() {
        val previousCount : Int = binding.txtHotelRoomsCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.decreaseRoomCount(previousCount)
        binding.txtHotelRoomsCount.text = currentCount.toString()
    }

    private fun increaseRoomCount() {
        val previousCount : Int = binding.txtHotelRoomsCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.increaseRoomCount(previousCount)
        binding.txtHotelRoomsCount.text = currentCount.toString()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.imgHotelAuditDecrease.id -> decreaseAuditCount()
            binding.imgHotelAuditIncrease.id -> increaseAuditCount()
            binding.imgHotelRoomsDecrease.id -> decreaseRoomCount()
            binding.imgHotelRoomsIncrease.id -> increaseRoomCount()
            binding.layoutHotelSort.id -> sortDialog()
            binding.layoutHotelLanguage.id -> languageDialog()
        }
    }

    private fun languageDialog() {

    }

    private fun sortDialog() {
        val builderSingle = AlertDialog.Builder(context)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            AppConstants.FilterOptions.values()
        )
        builderSingle.setAdapter(
            adapter
        ) { _, which ->
            binding.txtHotelSort.text = AppConstants.FilterOptions.values()[which].toString()
        }
        builderSingle.show()
    }
}
