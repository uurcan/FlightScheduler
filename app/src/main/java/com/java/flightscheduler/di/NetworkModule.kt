package com.java.flightscheduler.di

import android.content.Context
import com.java.flightscheduler.BuildConfig
import com.java.flightscheduler.data.remote.api.MockInterceptor
import com.java.flightscheduler.data.remote.api.services.FlightService
import com.java.flightscheduler.data.remote.api.services.MetricsService
import com.java.flightscheduler.enableLogging
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://test.api.amadeus.com/v1/")
        //.client(okHttpClient)
        .build()

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
        header : Interceptor,
        mockInterceptor : MockInterceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10L,TimeUnit.SECONDS)
        .readTimeout(10L,TimeUnit.SECONDS)
        .writeTimeout(10L,TimeUnit.SECONDS)
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
    fun provideMetrics(retrofit: Retrofit) : MetricsService = retrofit.create(MetricsService::class.java)
}