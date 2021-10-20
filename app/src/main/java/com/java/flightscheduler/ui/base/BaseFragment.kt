package com.java.flightscheduler.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VM: ViewModel?,
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
}