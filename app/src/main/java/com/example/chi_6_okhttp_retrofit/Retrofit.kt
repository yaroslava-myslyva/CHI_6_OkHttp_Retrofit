package com.example.chi_6_okhttp_retrofit

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("/animals/rand/10/")
    fun getResponseItem(): Call<List<Animal>>
}

object Common {
    private val BASE_URL = "https://zoo-animal-api.herokuapp.com/"
    val retrofitService: RetrofitService
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            val client = OkHttpClient().newBuilder()

            retrofit = Retrofit.Builder()
                .client(client.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            Log.d("ttt", "getClient")
        }
        return retrofit!!
    }
}