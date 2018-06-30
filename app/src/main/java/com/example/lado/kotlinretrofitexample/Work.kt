package com.example.lado.kotlinretrofitexample

import android.util.Log
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



class Work {
    private val URL: String = "http://YOUR_HOST:3000/"

    fun start() {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val messageAPI = retrofit.create(MessageAPI::class.java)

        val call: Call<Message> = messageAPI.getUserById(1)
        call.enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>?, t: Throwable?) {
                Log.d("FAIL1", t.toString())
            }

            override fun onResponse(call: Call<Message>?, response: Response<Message>?) {
                var post: Message? = response?.body()
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.d("INFO", "Name " + post!!.firstName + " " + post.email)
                    }
                    else {
                        Log.d("CODE1", "CODE IS " + response.code());
                    }
                }
            }

        })
    }

    fun json() {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val messageAPI = retrofit.create(MessageAPI::class.java)

        val calling: Call<JsonElement> = messageAPI.json()

        calling.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.d("FAIL1", t.toString())
            }

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                if(response != null) {
                    if(response.isSuccessful) {
                        val jsonElement: JsonElement? = response?.body()
                        Log.d("JSON", jsonElement.toString())
                    }
                    else {
                        Log.d("CODE1", "CODE IS " + response.code());
                    }
                }
            }

        })
    }
}
