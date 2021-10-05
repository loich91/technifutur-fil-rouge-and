package com.technipixl.filrouge.UI

import retrofit2.Response

class ConnexionChuckImpl: RetrofitChuck() {
    suspend fun getJoke(): Response<ListDataChuck> = getRetrofit().create(ConnexionChuck::class.java).chuckjoke()
}