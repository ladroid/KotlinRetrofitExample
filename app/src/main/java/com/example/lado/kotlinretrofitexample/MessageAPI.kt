package com.example.lado.kotlinretrofitexample

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageAPI {

    @GET("employees/{id}")
    fun getUserById(@Path("id") id: Int) : Call<Message>

    @GET("employees/")
    fun json() : Call<JsonElement>

    @POST("employees/")
    fun NewUser(@Body message: Message) : Call<Message>
}