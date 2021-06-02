package com.java.flightscheduler.data.remote.api.base

import retrofit2.Response

typealias BaseApiResponse<T> = Response<BaseApiResult.Success<T>>