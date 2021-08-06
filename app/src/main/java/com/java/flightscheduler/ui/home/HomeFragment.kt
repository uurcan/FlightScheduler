package com.java.flightscheduler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),View.OnClickListener{

    //override val layoutId : Int = R.layout.fragment_home
    private val viewModel : HomeViewModel by viewModels()
    lateinit var navController : NavController
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
    }

    private fun initializeViews(view : View) {
        navController = Navigation.findNavController(view)
        binding.buttonFlightOffersMain.setOnClickListener(this)
        binding.buttonHotelOffersMain.setOnClickListener(this)
        viewModel.text.observe(viewLifecycleOwner, {
            binding.textLocationName.text = it.name
            binding.textLocationCountry.text = it.country
        })
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            binding.buttonFlightOffersMain.id -> navController.navigate(R.id.action_nav_home_to_nav_flight_search)
            binding.buttonHotelOffersMain.id -> navController.navigate(R.id.action_nav_home_to_nav_hotel_search)
        }
    }
}
