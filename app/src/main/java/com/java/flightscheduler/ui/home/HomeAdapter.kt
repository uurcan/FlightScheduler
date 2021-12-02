package com.java.flightscheduler.ui.home

import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.PAGER_ITEM_SIZE
import com.java.flightscheduler.data.model.home.Places
import com.java.flightscheduler.data.repository.LocationRepository
import com.java.flightscheduler.databinding.LocationListItemBinding
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class HomeAdapter : BaseAdapter<Places, LocationListItemBinding>(R.layout.home_background_item) {
    private var locations = LocationRepository().initializeMockPlaces()

    override fun onBind(holder: BaseViewHolder, position: Int) {
        binding?.setVariable(BR.places, locations[position])
    }

    override fun getItemCount(): Int = PAGER_ITEM_SIZE
}