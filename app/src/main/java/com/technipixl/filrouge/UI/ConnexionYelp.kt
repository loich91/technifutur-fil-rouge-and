package com.technipixl.filrouge.UI

import com.technipixl.filrouge.Model.DataYelp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ConnexionYelp {
    @Headers("Content-type: application/json",
        "Authorization: Bearer VgLtpsYEjCFu4AR7byY2GWc7s_K5yldVHTSvkyifJZj_eQAI61zpnx15GE5AbyqMicZwFzlbFRTIjEHv4M-F0FQwO8p_17SMt4cZB9RsG3FSdKwr5dYB8R-YssBVYXYx"
        )
    @GET("businesses/search")
    suspend fun search(
        @Query("latitude",encoded = false)latitude:Double,
        @Query("longitude",encoded = false)longitude:Double,
        @Query("limit",encoded = false)limit:Int
    ):Response<DataYelp>
    suspend fun parc(
        @Query("latitude",encoded = false)latitude:Double,
        @Query("longitude",encoded = false)longitude:Double,
        @Query("limit",encoded = false)limit:Int,
        @Query("term",encoded = false)term:String
    ):Response<DataYelp>

}
