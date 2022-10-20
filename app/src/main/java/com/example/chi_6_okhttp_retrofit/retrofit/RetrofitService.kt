package com.example.chi_6_okhttp_retrofit.retrofit

import com.example.chi_6_okhttp_retrofit.Animal
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("/animals/rand/10/")
    fun getResponseItem(): Call<List<Animal>>
}



