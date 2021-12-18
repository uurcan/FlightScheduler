package com.java.flightscheduler.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.utils.extension.dismissLoadingDialog
import com.java.flightscheduler.utils.extension.showDialog
import com.java.flightscheduler.utils.extension.showLoadingDialog

abstract class BaseFragment<VM : BaseViewModel?, DB : ViewDataBinding?>(@LayoutRes private val layoutId: Int) : Fragment() {

    protected abstract val viewModel: VM?
    protected var binding: DB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeEvents()
        initBinding(inflater, container)
        return binding?.root
    }

    /**
     * initializes binding following DB class
     */
    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate<DB>(inflater, layoutId, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        onBind()
    }

    /**
     * do your logic here, binding is ready at this point
     */
    abstract fun onBind()

    /**
     * override this if not use loading dialog (example progress bar)
     */

    private fun observeEvents() {
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
        displayErrorMessage(message)
    }

    protected fun displayErrorMessage(message: String?) {
        showDialog(
            message = message,
            textPositive = getString(R.string.ok),
            positiveListener = { previousFragment() }
        )
    }
    private fun previousFragment() {
        findNavController().popBackStack()
    }

    protected open val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    initializeDateParser(it)
                }
            }
        }

    protected open fun initializeDateParser(it: Intent) {}
}