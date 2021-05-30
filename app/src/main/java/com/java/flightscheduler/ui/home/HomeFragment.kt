package com.java.flightscheduler.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentHomeBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,BaseViewModel>() {

    override val layoutId : Int = R.layout.fragment_home
    override val viewModel : HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textLocationName.text = it.name
            textLocationCountry.text = it.country
        })
    }
}
