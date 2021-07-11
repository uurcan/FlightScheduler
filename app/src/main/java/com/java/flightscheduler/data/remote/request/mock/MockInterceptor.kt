package com.java.flightscheduler.data.remote.request.mock

import android.content.res.AssetManager
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.HttpURLConnection
import kotlin.jvm.Throws

class MockInterceptor (private val assets : AssetManager): Interceptor{
    companion object {
        private const val MOCK_FLIGHT_DATA = "mock_flight_data.json"
    }
    @Throws(IllegalAccessError::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val responseString = when {
            uri.contains("default") -> ""
            else -> null
        }
        return if (responseString.isNullOrBlank()) {
            chain.proceed(chain.request())
        } else {
            chain.proceed(chain.request())
                .newBuilder()
                .code(HttpURLConnection.HTTP_OK)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type","application/json")
                .build()
        }
    }
}
