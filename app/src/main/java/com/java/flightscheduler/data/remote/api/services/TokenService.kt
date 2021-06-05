package com.java.flightscheduler.data.remote.api.services

import com.java.flightscheduler.data.model.auth.AccessToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenService {
    @FormUrlEncoded
    @POST("v1/security/oauth2/token")
    fun getAccessToken(
        @Field("client_id") clientId : String,
        @Field("client_secret") clientSecret : String,
        @Field("grant_type") grantType : String = "client_credentials"
    ) : Call<AccessToken>
}