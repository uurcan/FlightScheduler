package com.java.flightscheduler.ui.base

import androidx.databinding.ViewDataBinding

interface BaseRecyclerAdapter <Item : Any, ViewBinding : ViewDataBinding> {
    fun getLayoutRes(viewType : Int):Int

    fun bindFirstTime(binding : ViewBinding) {}

    fun bindView(binding : ViewBinding, item : Item , position : Int)
}