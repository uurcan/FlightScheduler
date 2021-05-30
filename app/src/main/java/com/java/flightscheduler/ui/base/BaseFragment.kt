package com.java.flightscheduler.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.java.flightscheduler.R
import com.java.flightscheduler.utils.dismissLoadingDialog
import com.java.flightscheduler.utils.showDialog
import com.java.flightscheduler.utils.showLoadingDialog
import java.lang.IllegalStateException

abstract class BaseFragment <ViewBinding : ViewDataBinding , ViewModel : BaseViewModel> : Fragment(){
    protected lateinit var viewBinding : ViewBinding
    protected abstract val viewModel : ViewModel

    @get:LayoutRes
    protected abstract val layoutId : Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        viewBinding.apply {
            setVariable(BR._all,viewModel)//TODO()
            viewBinding.lifecycleOwner = viewLifecycleOwner
            root.isClickable = true
            executePendingBindings()
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerEvents()
    }
    private fun observerEvents() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner, Observer{
                handleLoading(it == true)
            })
            errorMessage.observe(viewLifecycleOwner, Observer{
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(viewLifecycleOwner, Observer{
                handleErrorMessage(getString(R.string.text_no_internet_connection))
            })
            connectTimeOutEvent.observe(viewLifecycleOwner, Observer{
                handleErrorMessage(getString(R.string.text_connect_timeout))
            })
            forceUpdateAppEvent.observe(viewLifecycleOwner, Observer{
                handleErrorMessage(getString(R.string.text_update_app))
            })
            serverMaintainErrorEvent.observe(viewLifecycleOwner, Observer{
                handleErrorMessage(getString(R.string.text_server_maintain))
            })
            unknownErrorEvent.observe(viewLifecycleOwner, Observer{
                handleErrorMessage(getString(R.string.text_unknown_error))
            })
        }
    }

    private fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank())
            return

        dismissLoadingDialog()
        showDialog(
            message = message,
            textPositive =  "OK"
        )
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLoadingDialog()
    }

    fun navigateUp() {
        getNavController()?.navigateUp()
    }

    private fun getNavController(): NavController? {
        return try {
            NavHostFragment.findNavController(this)
        } catch (e : IllegalStateException) {
            null
        }
    }

    @SuppressLint("WrongConstant")
    private fun commitTransaction(transaction: FragmentTransaction,
                                  addToBackStack : Boolean = false,
                                  transit : Int = -1){
        if (addToBackStack) transaction.addToBackStack(null)
        if (transit != -1) transaction.setTransition(transit)
        transaction.commit()
    }
}