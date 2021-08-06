package com.java.flightscheduler.ui.base.paging

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.java.flightscheduler.R
import com.java.flightscheduler.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BasePagingFragment <ViewBinding : ViewDataBinding, ViewModel : BasePagingViewModel<Item>,Item : Any>
    :BaseFragment<ViewBinding,ViewModel>(){
    abstract val pagingAdapter : BasePagingAdapter<Item, out ViewDataBinding>

    abstract val swipeRefreshLayout : SwipeRefreshLayout?

    abstract val recyclerView : RecyclerView?
    //todo might be applied to linear layout manager
    open fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context,1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPaging()
    }
    //------paginationSetup starts---------
    private fun setupPaging() {
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.doRefresh()
        }
        recyclerView?.layoutManager = getLayoutManager()
        recyclerView?.adapter = pagingAdapter
        recyclerView?.setHasFixedSize(true)

        lifecycleScope.launch{
            viewModel.items.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
        pagingAdapter.addLoadStateListener {
            viewModel.handleLoadStates(
                combinedLoadStates = it,
                itemCount = pagingAdapter.itemCount
            )
        }
    }
    //------paginationSetup ends-----------

}