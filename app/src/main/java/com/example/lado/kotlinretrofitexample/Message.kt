package com.example.lado.kotlinretrofitexample

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Message(@SerializedName("id") val id: Int,
              @SerializedName("firstname") val firstName: String,
              @SerializedName("lastname") val lastName: String,
              @SerializedName("email") val email: String)