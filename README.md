# Flight Scheduler ✈️

![](app/flight_scheduler.png)

## Table of Contents
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Package Structure](#package-structure)
* [Screenshots](#screenshots)
* [Architecture](#architecture)
* [To-Do](#to-do)
* [Setup](#setup)

## Technologies Used 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Glide](https://github.com/bumptech/glide) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.


## Features
- Search Flight and Hotel offers on global scope.
- Display itinerary price metrics regarding the previous flight dates.
- Check the status of the ongoing flight.
- Display Seat Maps of the flights.
- Get the delay prediction of the upcoming flights.

# Package Structure
    
    com.java.flightscheduler    # Root Package
    .
    ├── data                # For data handling.
    │   ├── local           # Local Persistence Database. Room (SQLite) database
    |   │   ├── dao         # Data Access Object for Room   
    │   ├── remote          # Remote Data Handlers     
    |   │   ├── api         # Retrofit API for remote end point.
    │   └── repository      # Single source of data.
    |
    ├── model               # Model classes
    |
    ├── di                  # Dependency Injection             
    │   ├── builder         # Activity Builder
    │   ├── component       # DI Components       
    │   └── module          # DI Modules
    |
    ├── ui                  # Activity/View layer
    │   ├── base            # Base View
    │   ├── main            # Main Screen Activity & ViewModel
    |   │   ├── adapter     # Adapter for RecyclerView
    |   │   └── viewmodel   # ViewHolder for RecyclerView   
    │   └── prediction      # Delay Prediction of Flights
    |   │   ├── search      # Prediction search component
    |   │   └── results     # Prediction results component
    │   └── flight          # Flight offers and it's details
    |   │   ├── search      # Flight search component
    |   │   ├── results     # Flight results component
    |   │   └── details     # Flight details component
    │   └── hotel           # Hotel offers and it's details
    |   │   ├── search      # Hotel search component
    |   │   ├── results     # Hotel results component
    |   │   └── details     # Hotel details component
    │   └── seatmap         # Seatmap
    |      └── results      # Seatmap results
    |
    └── utils               # Utility Classes / Kotlin extensions


## Setup
In order to run project on local, APP_SECRET AND APP_KEY required. Please check https://developers.amadeus.com for more information
Retrieved KEY files should be stored in [gradle.properties file](gradle.properties)

```properties
API_KEY = "YOUR_API_KEY"
API_SECRET = "YOUR_API_SECRET"
```

All test cases are located in androidTest package, during the test implementation, dependency injection is being handled by Hilt
In order to run test cases, Virtual device (Emulator) or Physical device needs to be connected.

* Network tests are located in [here](app/src/androidTest/java/com/java/flightscheduler/network)
* UI tests are located in [here](app/src/androidTest/java/com/java/flightscheduler/ui)
* Apk file can be located in [here](art)

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## TODO
* Preferences option
* Itinerary metrics visualisation
