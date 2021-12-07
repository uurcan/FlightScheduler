package com.java.flightscheduler.di

import com.java.flightscheduler.BuildConfig
import com.java.flightscheduler.data.repository.TokenRepository
import com.java.flightscheduler.utils.NumbersAdapter
import com.java.flightscheduler.utils.TypesAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@TestInstallIn(components = [SingletonComponent::class],
    replaces = [NetworkModule::class])
object TestNetworkModule {
    @Provides
    fun provideBaseUrl(): String = "https://test.api.amadeus.com/"

    @Provides
    fun provideToken(): OkHttpClient = TokenRepository.Builder()
        .setClientId(BuildConfig.API_KEY)
        .setClientSecret(BuildConfig.API_SECRET)
        .build()
        .tokenClient

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(provideBaseUrl())
        .client(provideToken())
        .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(NumbersAdapter.FACTORY)
        .add(TypesAdapterFactory())
        .add(KotlinJsonAdapterFactory())
        .build()
}