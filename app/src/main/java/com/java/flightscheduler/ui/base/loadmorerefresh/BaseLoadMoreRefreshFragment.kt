package com.java.flightscheduler.ui.base.loadmorerefresh

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.java.flightscheduler.R
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.BaseListAdapter

abstract class BaseLoadMoreRefreshFragment<ViewBinding : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item : Any>
    :BaseFragment<ViewBinding, ViewModel>(){
    override val layoutId: Int = R.layout.fragment_load_more_refresh
    abstract val listAdapter : BaseListAdapter<Item,out ViewDataBinding>
    abstract val swipeRefreshLayout : SwipeRefreshLayout?
    abstract val recyclerView : RecyclerView?

    open fun getLayoutManager() : RecyclerView.LayoutManager =
        LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadMoreRefresh()
    }

    private fun setupLoadMoreRefresh() {
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.doRefresh()
        }
        recyclerView?.layoutManager = getLayoutManager()
        recyclerView?.adapter = listAdapter
        recyclerView?.setHasFixedSize(true)
        viewModel.apply {
            itemList.observe(viewLifecycleOwner, Observer {
                listAdapter.submitList(it)
            })
            firstLoad()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView?.adapter = null
    }
}