package com.java.flightscheduler.di

import android.content.Context
import com.java.flightscheduler.BuildConfig
import com.java.flightscheduler.data.remote.api.ApiService
import com.java.flightscheduler.data.remote.api.MockInterceptor
import com.java.flightscheduler.data.remote.api.services.FlightServices
import com.java.flightscheduler.enableLogging
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        private const val CONNECTION_TIMEOUT = 10L
    }
    @Singleton
    @Provides
    fun provideOkHttpCache(context: Context) : Cache = Cache(context.cacheDir,(10 * 1024 * 1024).toLong())

    @Singleton
    @Provides
    @Named("logging")
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor {
                chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key","")
                .build()
            val newRequest = request.newBuilder()
                .url(newUrl)
                .addHeader("Authorization","Bearer " + "token")
                .method(request.method,request.body)
                .build()
            chain.proceed(newRequest)
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("header") header : Interceptor,
        @Named("mock") mockInterceptor : MockInterceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT,TimeUnit.SECONDS)
        .readTimeout(CONNECTION_TIMEOUT,TimeUnit.SECONDS)
        .writeTimeout(CONNECTION_TIMEOUT,TimeUnit.SECONDS)
        .addInterceptor(header)
        .apply {
            if (enableLogging()) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
            if (BuildConfig.DEBUG) addInterceptor(mockInterceptor)

        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        moshi: Moshi) : Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun getFlights(retrofit: Retrofit) : FlightServices = retrofit.create(FlightServices::class.java)
}