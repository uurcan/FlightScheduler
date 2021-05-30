package com.java.flightscheduler.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder <ViewBinding : ViewDataBinding>(val binding : ViewBinding) : RecyclerView.ViewHolder(binding.root)