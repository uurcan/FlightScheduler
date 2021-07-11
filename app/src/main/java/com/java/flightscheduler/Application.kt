package com.java.flightscheduler

import android.app.Application
import androidx.multidex.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlightApplication : Application()
fun enableLogging() = BuildConfig.BUILD_TYPE != "release"
