package com.technipixl.filrouge.UI

import com.technipixl.filrouge.Model.DataYelp
import retrofit2.Response
import retrofit2.create

class ConnexionYelpImpl : RetrofitYelp(){
    suspend fun getSearch( latitude:Double, longitude:Double, limit:Int ):Response<DataYelp> = getRetrofitYelp().create(ConnexionYelp::class.java).search(latitude,longitude,limit)
    suspend fun getPark( latitude:Double, longitude:Double, limit:Int ):Response<DataYelp> = getRetrofitYelp().create(ConnexionYelp::class.java).parc(latitude,longitude,limit)
}