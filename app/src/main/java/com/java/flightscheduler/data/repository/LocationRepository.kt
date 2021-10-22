package com.java.flightscheduler.data.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.home.Places
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class LocationRepository @Inject constructor(private val context: Context) {
    fun getPlaces() : Places {
        val placesList : ArrayList<Places> = ArrayList()
        try {
            val inputStream : InputStream = context.resources.openRawResource(R.raw.locations)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                placesList.add(
                    Places(
                        tokens[0].toInt(),
                        tokens[1],
                        tokens[2]
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return placesList.random()
    }

    fun initializeMockPlaces() : List<Places> {
        val locationList = arrayListOf<Places>()
        locationList.add(Places(R.drawable.abu_dhabi_uae,"UAE","Abu Dhabi"))
        locationList.add(Places(R.drawable.agra_india,"India","Agra"))
        locationList.add(Places(R.drawable.alesund_norway,"Norway","Alesund"))
        locationList.add(Places(R.drawable.athens_greece,"Greece","Athens"))
        locationList.add(Places(R.drawable.ayyuthaya_thailand,"Ayyuthaya","Thailand"))
        locationList.add(Places(R.drawable.bali_indonesia,"Bali","Indonesia"))
        locationList.add(Places(R.drawable.burano_italy,"Burano","Italy"))
        locationList.add(Places(R.drawable.california_usa,"California","United States"))
        locationList.add(Places(R.drawable.cape_town_sout_africa,"Cape Town","South Africa"))
        locationList.add(Places(R.drawable.chiang_mai_thailand,"Chiang Mai","Thailand"))
        locationList.add(Places(R.drawable.cinque_terre_italy,"Cinque Terre","Italy"))
        locationList.add(Places(R.drawable.copenhagen_denmark,"Copenhagen","Denmark"))
        locationList.add(Places(R.drawable.delhi_india,"Delhi","India"))
        locationList.add(Places(R.drawable.harstad_norway,"Harstad","Norway"))
        locationList.add(Places(R.drawable.lisbon_portugal,"Lisbon","Portugal"))
        locationList.add(Places(R.drawable.marrakesh_morocco,"Marrakesh","Morocco"))
        locationList.add(Places(R.drawable.menton_france,"Menton","France"))
        locationList.add(Places(R.drawable.nouvelle_aquitaine_france,"Nouvelle Aquitaine","France"))
        locationList.add(Places(R.drawable.oslo_norway_temp,"Oslo","Norway"))
        locationList.add(Places(R.drawable.palawan_philippines,"Palawan","Philippines"))
        locationList.add(Places(R.drawable.portmeirion_wales,"Portmeirion","Wales"))
        locationList.add(Places(R.drawable.positano_italy,"Positano","Italy"))
        locationList.add(Places(R.drawable.gdansk_poland,"Gdansk","Poland"))
        locationList.add(Places(R.drawable.sighisoara_romania,"Sighisoara","Romania"))
        locationList.add(Places(R.drawable.stockholm_sweden,"Stockholm","Sweden"))
        locationList.shuffle()
        return locationList
    }
}
