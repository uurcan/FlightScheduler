package com.java.flightscheduler.data.remote.request.base

import com.java.flightscheduler.data.model.base.BaseApiResult
import retrofit2.Response

typealias BaseApiResponse<T> = Response<BaseApiResult.Success<T>>