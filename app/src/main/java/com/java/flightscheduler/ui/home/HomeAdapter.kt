package com.java.flightscheduler.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.PAGER_ITEM_SIZE
import com.java.flightscheduler.data.model.home.Places
import com.java.flightscheduler.data.repository.LocationRepository
import com.java.flightscheduler.databinding.LocationListItemBinding
import com.java.flightscheduler.utils.setImageDrawable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeAdapter constructor(context: Context) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val locationRepository = LocationRepository(context)
    private var locations : List<Places>

    init {
        locations = locationRepository.initializeMockPlaces()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.home_background_item , parent , false))
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int = PAGER_ITEM_SIZE

    inner class ViewHolder(private var locationListItemBinding: LocationListItemBinding) :
        RecyclerView.ViewHolder(locationListItemBinding.root) {
        //todo
        //private val homeViewModel: HomeViewModel = HomeViewModel(locationRepository)
        fun bind(
            place: Places
        ) {
            locationListItemBinding.imgHomePlaces.setImageDrawable(place.image)
            locationListItemBinding.textLocationCountry.text = place.name
            locationListItemBinding.textLocationName.text = place.country
        }
    }
}