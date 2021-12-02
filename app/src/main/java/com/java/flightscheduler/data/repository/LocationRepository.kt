package com.java.flightscheduler.data.repository

import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.PAGER_ITEM_SIZE
import com.java.flightscheduler.data.model.home.Places
import javax.inject.Inject

class LocationRepository @Inject constructor() {

    fun initializeMockPlaces(): List<Places> {
        val locationList = arrayListOf<Places>()
        locationList.add(Places(R.drawable.abu_dhabi_uae, "Abu Dhabi", "UAE"))
        locationList.add(Places(R.drawable.agra_india, "Agra", "India"))
        locationList.add(Places(R.drawable.alesund_norway, "Alesund", "Norway"))
        locationList.add(Places(R.drawable.athens_greece, "Athens", "Greece"))
        locationList.add(Places(R.drawable.ayyuthaya_thailand, "Ayyuthaya", "Thailand"))
        locationList.add(Places(R.drawable.bali_indonesia, "Bali", "Indonesia"))
        locationList.add(Places(R.drawable.burano_italy, "Burano", "Italy"))
        locationList.add(Places(R.drawable.california_usa, "California", "United States"))
        locationList.add(Places(R.drawable.cape_town_sout_africa, "Cape Town", "South Africa"))
        locationList.add(Places(R.drawable.chiang_mai_thailand, "Chiang Mai", "Thailand"))
        locationList.add(Places(R.drawable.cinque_terre_italy, "Cinque Terre", "Italy"))
        locationList.add(Places(R.drawable.copenhagen_denmark, "Copenhagen", "Denmark"))
        locationList.add(Places(R.drawable.delhi_india, "Delhi", "India"))
        locationList.add(Places(R.drawable.harstad_norway, "Harstad", "Norway"))
        locationList.add(Places(R.drawable.lisbon_portugal, "Lisbon", "Portugal"))
        locationList.add(Places(R.drawable.marrakesh_morocco, "Marrakesh", "Morocco"))
        locationList.add(Places(R.drawable.menton_france, "Menton", "France"))
        locationList.add(Places(R.drawable.nouvelle_aquitaine_france, "Nouvelle Aquitaine", "France"))
        locationList.add(Places(R.drawable.oslo_norway_temp, "Oslo", "Norway"))
        locationList.add(Places(R.drawable.palawan_philippines, "Palawan", "Philippines"))
        locationList.add(Places(R.drawable.portmeirion_wales, "Portmeirion", "Wales"))
        locationList.add(Places(R.drawable.positano_italy, "Positano", "Italy"))
        locationList.add(Places(R.drawable.gdansk_poland, "Gdansk", "Poland"))
        locationList.add(Places(R.drawable.sighisoara_romania, "Sighisoara", "Romania"))
        locationList.add(Places(R.drawable.stockholm_sweden, "Stockholm", "Sweden"))
        locationList.add(Places(R.drawable.istanbul_turkey, "Istanbul", "Turkey"))
        locationList.shuffle()
        return locationList.take(PAGER_ITEM_SIZE)
    }
}
