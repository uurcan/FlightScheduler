package com.java.flightscheduler

import androidx.activity.viewModels
import com.java.flightscheduler.databinding.ActivityMainBinding
import com.java.flightscheduler.ui.base.BaseActivity
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(){
    override val viewModel: BaseViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_main
}
