package com.java.flightscheduler.di

import android.content.Context
import com.java.flightscheduler.di.flight.FlightModule.provideToken
import com.java.flightscheduler.utils.adapters.BigDecimalJsonAdapter
import com.java.flightscheduler.utils.adapters.NumbersAdapter
import com.java.flightscheduler.utils.adapters.TypesAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://test.api.amadeus.com/")
        .client(provideToken())
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
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(NumbersAdapter.FACTORY)
        .add(TypesAdapterFactory())
        .addLast(KotlinJsonAdapterFactory())
        .build()
}