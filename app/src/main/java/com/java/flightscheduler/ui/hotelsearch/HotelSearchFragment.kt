package com.java.flightscheduler.ui.hotelsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R
import kotlinx.android.synthetic.main.fragment_gallery.*

class HotelSearchFragment : Fragment() {
    private lateinit var hotelSearchFragment : HotelSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotelSearchFragment = ViewModelProvider(this).get(HotelSearchViewModel::class.java)
        hotelSearchFragment.getHotelData()?.observe(viewLifecycleOwner, Observer {
                hotelData ->
            if (hotelData != null)
                text_gallery.text = hotelData[0].hotel?.contact?.phone.toString()
        })
    }
}
