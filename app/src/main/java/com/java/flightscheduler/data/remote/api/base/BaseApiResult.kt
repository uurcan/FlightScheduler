package com.java.flightscheduler.data.remote.api.base

import com.java.flightscheduler.data.constants.HttpPagination
import com.squareup.moshi.JsonClass
import java.lang.Exception


sealed class BaseApiResult <out R>{
    var method : String? = null
        internal set

    var code: Int = 0
        internal set

    data class Success<out T> internal constructor(
        val data : T,
        val meta : MetaResult?,
        val dictionaries : Map<String,Any>,
        val warnings : List<Map<String,Any>>
    ) : BaseApiResult<T>(){
        @JsonClass(generateAdapter = true)
        data class MetaResult(val count: Int?, val links: Map<String,String>?)

        fun hasFirst() = hasMeta(HttpPagination.FIRST)
        fun hasLast() = hasMeta(HttpPagination.LAST)
        fun hasNext() = hasMeta(HttpPagination.NEXT)
        fun hasSelf() = hasMeta(HttpPagination.SELF)
        fun hasPrevious() = hasMeta(HttpPagination.PREVIOUS)

        private fun hasMeta(key : String) = meta?.links?.get(key) != null
    }
    @JsonClass(generateAdapter = true)
    data class Error internal constructor(
        val errors: List<Issue> = ArrayList(),
        val exception : Exception? = null
    ) : BaseApiResult<Nothing>() {
        @JsonClass(generateAdapter = true)
        data class Issue internal constructor(
            val status : Int? = null,
            val code: Int? = null,
            val title : String? = null,
            val detail : String? = null,
            val source : String? = null
        )
    }

    @JsonClass(generateAdapter = true)
    data class Source internal constructor(
        val pointer : String? = null,
        val parameter : String? = null,
        val example : String? = null
    )
}
val BaseApiResult<*>.succeeded
    get() = this is BaseApiResult.Success && data != null