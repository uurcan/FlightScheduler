package com.java.flightscheduler.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R
import com.java.flightscheduler.utils.dismissLoadingDialog
import com.java.flightscheduler.utils.showDialog
import com.java.flightscheduler.utils.showLoadingDialog

abstract class BaseFragment<VM: BaseViewModel?,
        DB: ViewDataBinding?>(@LayoutRes private val layoutId: Int) : Fragment() {

    protected var viewModel: VM? = null
    protected var binding: DB? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        init()
        setViewModelFactory()?.let { factory ->
            initViewModel(factory, setViewModelClass())
        }
        observerEvents()
        initBinding(inflater, container)
        return binding?.root
    }

    /**
     * method for all initial steps like dagger setup, firebase creation, etc.
     */
    abstract fun init()

    /**
     * returns instance of the ViewModel factory
     */
    abstract fun setViewModelFactory(): ViewModelProvider.Factory?

    /**
     * returns instance of the ViewModel factory
     */
    abstract fun setViewModelClass(): Class<VM>

    /**
     * creates an instance of the ViewModel
     * that follows that class returned in setViewModelClass()
     */
    private fun initViewModel(factory: ViewModelProvider.Factory, vmClass: Class<VM>) {
        viewModel = ViewModelProvider(this, factory).get(vmClass)
    }

    /**
     * initializes binding following DB class
     */
    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate<DB>(inflater, layoutId, container, false)
        onBind()
    }

    /**
     * do your logic here, binding is ready at this point
     */
    abstract fun onBind()

    /**
     * override this if not use loading dialog (example progress bar)
     */

    private fun observerEvents() {
        viewModel?.apply {
            isLoading.observe(viewLifecycleOwner) {
                handleLoading(it == true)
            }
            errorMessage.observe(viewLifecycleOwner) {
                handleErrorMessage(it)
            }
            noInternetConnectionEvent.observe(viewLifecycleOwner) {
                handleErrorMessage(getString(R.string.no_internet_connection))
            }
            connectTimeOutEvent.observe(viewLifecycleOwner) {
                handleErrorMessage(getString(R.string.connect_timeout))
            }
            forceUpdateAppEvent.observe(viewLifecycleOwner) {
                handleErrorMessage(getString(R.string.force_update_app))
            }
            serverMaintainErrorEvent.observe(viewLifecycleOwner) {
                handleErrorMessage(getString(R.string.server_maintain_message))
            }
            unknownErrorEvent.observe(viewLifecycleOwner) {
                handleErrorMessage(getString(R.string.unknown_error))
            }
        }
    }

    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLoadingDialog()
        showDialog(
            message = message,
            textPositive = getString(R.string.ok)
        )
    }
}