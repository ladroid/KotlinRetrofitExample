package com.example.lado.kotlinretrofitexample

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageAPI {

    @GET("employees/{id}")
    fun getUserById(@Path("id") id: Int) : Call<Message>

    @GET("employees/")
    fun json() : Call<JsonElement>
}