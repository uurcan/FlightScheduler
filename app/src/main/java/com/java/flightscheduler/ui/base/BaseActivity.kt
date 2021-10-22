package com.java.flightscheduler.ui.base

import android.os.Bundle
import android.view.Menu
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.utils.dismissLoadingDialog
import com.java.flightscheduler.utils.showDialog
import com.java.flightscheduler.utils.showLoadingDialog

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : AppCompatActivity() {
    private lateinit var viewBinding: ViewBinding
    protected abstract val viewModel : ViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration

    @get:LayoutRes
    protected abstract val layoutId : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this,layoutId)
        viewBinding.apply {
            root.isClickable = true
            executePendingBindings()
        }

        observeErrorEvent()
        initializeComponents()
    }

    private fun initializeComponents() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_flight_search,
            R.id.nav_hotel_search,
            R.id.nav_itinerary_metrics,
            R.id.nav_flight_status,
            R.id.nav_seat_map,
            R.id.nav_delay_prediction),
            drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun observeErrorEvent(){
        viewModel.apply {
            isLoading.observe(this@BaseActivity, Observer {
                handleLoading(it == true)
            })
            errorMessage.observe(this@BaseActivity, Observer {
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(this@BaseActivity, Observer{
                handleErrorMessage(getString(R.string.text_no_internet_connection))
            })
            connectTimeOutEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.text_connect_timeout))
            })
            forceUpdateAppEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.text_update_app))
            })
            serverMaintainErrorEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.text_server_maintain))
            })
            unknownErrorEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.text_unknown_error))
            })
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading)
            showLoadingDialog()
        else
            dismissLoadingDialog()
    }

    private fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank())
            return

        dismissLoadingDialog()
        showDialog(
            message = message,
            textPositive = "OK",
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}