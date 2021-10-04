package com.java.flightscheduler.data.model.base

import com.java.flightscheduler.data.constants.PaginationConstants
import com.squareup.moshi.JsonClass

sealed class BaseApiResult <out R>{
    var method : String? = null
        internal set

    var code: Int = 0
        internal set

    var url : String? = null
        internal set

    @JsonClass(generateAdapter = true)
    data class Success<out T> internal constructor(
        val data : T,
        val meta : MetaResult? = null,
        val dictionaries : Map<String,Any>? = null,
        val warnings : List<Map<String,Any>>? = null
    ) : BaseApiResult<T>(){
        @JsonClass(generateAdapter = true)
        data class MetaResult(val count: Int?, val links: Map<String,String>?)

        fun hasFirst() = hasMeta(PaginationConstants.FIRST)
        fun hasLast() = hasMeta(PaginationConstants.LAST)
        fun hasNext() = hasMeta(PaginationConstants.NEXT)
        fun hasSelf() = hasMeta(PaginationConstants.SELF)
        fun hasPrevious() = hasMeta(PaginationConstants.PREVIOUS)

        private fun hasMeta(key : String) = meta?.links?.get(key) != null
    }
    @JsonClass(generateAdapter = true)
    data class Error internal constructor(
        val errors: List<Issue> = ArrayList(),
    ) : BaseApiResult<Nothing>() {
        @JsonClass(generateAdapter = true)
        data class Issue internal constructor(
            val status : Int? = null,
            val code: Int? = null,
            val title : String? = null,
            val detail : String? = null
        )


        @JsonClass(generateAdapter = true)
        data class Source internal constructor(
            val pointer : String? = null,
            val parameter : String? = null,
            val example : String? = null
        )
    }
}
val BaseApiResult<*>.succeeded
    get() = this is BaseApiResult.Success && data != null