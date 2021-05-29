package com.java.flightscheduler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val locationName: TextView = root.findViewById(R.id.textLocationName)
        val locationCountry: TextView = root.findViewById(R.id.textLocationCountry)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            locationName.text = it.name
            locationCountry.text = it.country
        })
        return root
    }
}
