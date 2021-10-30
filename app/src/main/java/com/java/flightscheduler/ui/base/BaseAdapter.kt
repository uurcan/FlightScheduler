package com.java.flightscheduler.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T : Any, DB : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : ListAdapter<T, BaseViewHolder>(BaseItemCallback<T>()) {
    protected var binding: DB? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBind(holder,position)
    }
    abstract fun onBind(holder: BaseViewHolder, position: Int)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BaseViewHolder {
        initBinding(parent)
        return BaseViewHolder(binding?.root as ViewGroup)
    }

    private fun initBinding(parent: ViewGroup) {
        binding = DataBindingUtil.inflate<DB>(LayoutInflater.from(parent.context)
            , layoutId
            , parent
            , false)
    }

    override fun getItemCount() = currentList.size
}