package com.java.flightscheduler.ui.hotel.hoteldetails

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.databinding.FragmentHotelDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HotelDetailsFragment : Fragment() {
    private val args by navArgs<HotelDetailsFragmentArgs>()
    private val hotelDetailsViewModel : HotelDetailsViewModel by viewModels()
    private lateinit var binding : FragmentHotelDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hotel_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeResults(args.hotel)
        binding.setVariable(BR.hotelDetail,args.hotel)
    }

    private fun initializeResults(hotel: HotelOffer) {
        Log.d("AMATEST", hotel.hotel?.media?.get(0)?.uri.toString())
        //binding..text = hotel.offers?.get(0)?.checkInDate.toString()
    }
}