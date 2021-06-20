package com.java.flightscheduler.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.content.res.Resources
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {
    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context):Context = context

    @Singleton
    @Provides
    fun provideResources(context: Context):Resources = context.resources

    @Singleton
    @Provides
    fun provideAssetManager(context: Context):AssetManager = context.assets

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context):SharedPreferences = context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
}