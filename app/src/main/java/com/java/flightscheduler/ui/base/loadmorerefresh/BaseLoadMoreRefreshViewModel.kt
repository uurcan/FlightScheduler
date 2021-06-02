package com.java.flightscheduler.ui.base.loadmorerefresh

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.constants.TimeConstants
import com.java.flightscheduler.ui.base.BaseViewModel
import com.java.flightscheduler.utils.EndlessRecyclerOnScrollListener

abstract class BaseLoadMoreRefreshViewModel<Item>() : BaseViewModel() {
    val isRefreshing = MutableLiveData<Boolean>().apply { value = false }

    val isLoadMore = MutableLiveData<Boolean>().apply { value = false }

    private val currentPage = MutableLiveData<Int>().apply { value = getPreFirstPage() }

    private val isLastPage = MutableLiveData<Boolean>().apply { value = false }

    val onScrollListener = object : EndlessRecyclerOnScrollListener(getLoadMoreThreshold()){
        override fun onLoadMore() {
            doLoadMore()
        }
    }

    val itemList = MutableLiveData<ArrayList<Item>>()

    val isEmptyList = MutableLiveData<Boolean>().apply { value = false }

    abstract fun loadData(page : Int)

    private fun isFirst() = currentPage.value == getPreFirstPage() && itemList.value?.isEmpty() ?: true

    fun firstLoad() {
        if (isFirst()){
            showLoading()
            loadData(getFirstPage())
        }
    }

    fun doRefresh(){
        if (isLoading.value == true || isRefreshing.value == true) return

        isRefreshing.value = true
        refreshData()
    }

    private fun refreshData() {
        loadData(getFirstPage())
    }

    fun doLoadMore(){
        if (isLoading.value == true || isRefreshing.value == true || isLoadMore.value == true || isLastPage.value == true) return

        isLoadMore.value = true
        loadMore()
    }

    private fun loadMore() {
        loadData(currentPage.value?.plus(1) ?: getFirstPage())
    }

    open fun getFirstPage() = TimeConstants.DEFAULT_FIRST_PAGE

    private fun getPreFirstPage() = getFirstPage() - 1

    protected fun getLoadMoreThreshold() = TimeConstants.DEFAULT_NUM_VISIBLE_THRESHOLD

    protected open fun getNumberItemPerPage() = TimeConstants.DEFAULT_ITEM_PER_PAGE

    fun resetLoadMore() {
        onScrollListener.resetOnLoadMore()
        isLastPage.value = false
    }

    fun onLoadSuccess(page : Int, items : List<Item>?){
        currentPage.value = page

        if (currentPage.value == getFirstPage()) itemList.value?.clear()

        if (isRefreshing.value == true) resetLoadMore()

        val newList = itemList.value ?: ArrayList()
        newList.addAll(items ?: listOf())
        itemList.value = newList

        isLastPage.value = items?.size ?: 0 <getNumberItemPerPage()

        hideLoading()
        isRefreshing.value = false
        isLoadMore.value = false

        checkEmptyList()
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        onScrollListener.isLoading = false

        isRefreshing.value = false
        isLoadMore.value = false

        checkEmptyList()
    }

    private fun checkEmptyList(){
        isEmptyList.value = itemList.value?.isEmpty() ?: true
    }
}