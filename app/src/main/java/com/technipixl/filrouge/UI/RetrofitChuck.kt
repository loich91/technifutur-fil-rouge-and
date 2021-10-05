package com.technipixl.filrouge.UI


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitChuck {
    fun getRetrofit(): Retrofit {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }
        return Retrofit.Builder() //retour du retrofit
            .baseUrl("https://api.icndb.com/") //adresse de base
            .addConverterFactory(GsonConverterFactory.create()) //conversion du json en Gson
            .client(okBuilder.build()) // gestion du Temps
            .build() // construction de la requete

    }
}