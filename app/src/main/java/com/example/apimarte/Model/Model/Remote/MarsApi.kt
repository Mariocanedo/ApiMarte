package com.example.apimarte.Model.Model.Remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MarsApi {

    // vieja Confiable // sin corrutinas

    @GET("realestate")

    fun fetchMarsData(): Call<List<MarsRealState>>

    // con corrutinas
    @GET("realestate")
    fun fetchMarsDataCoroutines(): Response<List<MarsRealState>>


}