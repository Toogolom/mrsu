package com.example.mrsu.retrofit


import com.example.example.TimeTableToDate
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {
    @FormUrlEncoded
    @POST("OAuth/token")
    suspend fun getAccessToken(@Field("grant_type") grantType: String,
                               @Field("client_id") clientId: String,
                               @Field("client_secret") clientSecret: String,
                               @Field("username") username: String,
                               @Field("password") password: String):AccessToken

   @Headers(
       "Content-Type: application/json "
   )
    @GET("v1/User")
    suspend fun getUser(@Header("Authorization") token:String):User

    @GET("v1/StudentTimeTable")
    suspend fun getTimeTable(@Query("date") date:String, @Header("Authorization") token:String): ArrayList<TimeTableToDate>


}

