package com.java.flightscheduler.ui.base.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.java.flightscheduler.ui.base.BaseRecyclerAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

abstract class BasePagingAdapter<Item : Any, ViewBinding : ViewDataBinding>(
    callback: DiffUtil.ItemCallback<Item>
) : PagingDataAdapter<Item, BaseViewHolder<ViewBinding>>(callback),
    BaseRecyclerAdapter<Item, ViewBinding> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(DataBindingUtil.inflate<ViewBinding>(
            LayoutInflater.from(parent.context),
            getLayoutRes(viewType),
            parent,
            false
        ).apply {
            bindFirstTime(this)
        })
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        val item: Item? = getItem(position)
        //holder.binding.setVariable(BR._all,item)
        if (item != null) {
            bindView(holder.binding,item,position)
        }
        holder.binding.executePendingBindings()
    }
}