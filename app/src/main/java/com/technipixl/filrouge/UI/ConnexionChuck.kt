package com.technipixl.filrouge.UI

import com.technipixl.filrouge.UI.ChuckData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ConnexionChuck {
    @Headers("Content-type: application/json")
    @GET("jokes")
    suspend fun chuckjoke():Response<ListDataChuck>
}