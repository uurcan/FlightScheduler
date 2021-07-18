package com.java.flightscheduler.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.content.res.Resources
import com.java.flightscheduler.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
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
    fun provideInputStream(context: Context) : InputStream = context.resources.openRawResource(R.raw.airport_codes)

    @Singleton
    @Provides
    fun provideBufferedReader(context: Context) : BufferedReader = BufferedReader(InputStreamReader(provideInputStream(context), StandardCharsets.UTF_8))
}