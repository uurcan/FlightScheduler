package com.java.flightscheduler.data.remote.request.base

import com.java.flightscheduler.data.model.base.BaseApiResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Suppress("BlockingMethodInNonBlockingContext")
open class BaseApiCall(private val moshi: Moshi, private val dispatcher: CoroutineDispatcher) {

    suspend fun <T : Any> baseApiCall(call: suspend () -> BaseApiResponse<T>): BaseApiResult<T> {
        return withContext(dispatcher) {
            try {
                val response = call()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    body.apply {
                        method = response.raw().request.method
                        code = response.code()
                        url = response.raw().request.url.toString()
                    }
                } else {
                    moshi.adapter(BaseApiResult.Error::class.java)
                        .fromJson(response.errorBody()?.string() ?: "")
                        ?: BaseApiResult.Error()
                }
            } catch (ex: Exception) {
                BaseApiResult.Error()
            }
        }
    }

    fun bodyAsMap(body: String): Map<String, Any> {
        val type = Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            Any::class.java
        )
        return moshi.adapter<Map<String, Any>>(type).fromJson(body) ?: emptyMap()
    }
}
