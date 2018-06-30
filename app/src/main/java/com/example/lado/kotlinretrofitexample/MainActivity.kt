package com.example.lado.kotlinretrofitexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val URL: String = "http://YOUR_HOST:3000/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkId = findViewById(R.id.checkID) as CheckBox
        val checkJson = findViewById(R.id.checkJSON) as CheckBox
        val accept = findViewById(R.id.accept) as Button

        accept.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(checkId.isChecked) {
                    start()
                }
                if(checkJson.isChecked) {
                    json()
                }
            }

        })
    }

    fun start() {
        var output = findViewById<TextView>(R.id.Output)
        var enter = findViewById<EditText>(R.id.enter)
        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val numId: Int = enter.text.toString().toInt()
        val messageAPI = retrofit.create(MessageAPI::class.java)

        val call: Call<Message> = messageAPI.getUserById(numId)
        call.enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>?, t: Throwable?) {
                Log.d("FAIL1", t.toString())
            }

            override fun onResponse(call: Call<Message>?, response: Response<Message>?) {
                var post: Message? = response?.body()
                if (response != null) {
                    if (response.isSuccessful) {
                        output.setText(post!!.firstName + " " + post.email)
                        Log.d("INFO", "Name " + post!!.firstName + " " + post.email)
                    }
                    else {
                        Toast.makeText(this@MainActivity, "CODE IS " + response.code(), Toast.LENGTH_SHORT).show()
                        Log.d("CODE1", "CODE IS " + response.code());
                    }
                }
            }

        })
    }

    fun json() {
        var output = findViewById<TextView>(R.id.Output)
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
                        output.setText(jsonElement.toString())
                        Log.d("JSON", jsonElement.toString())
                    }
                    else {
                        Toast.makeText(this@MainActivity, "CODE IS " + response.code(), Toast.LENGTH_SHORT).show()
                        Log.d("CODE1", "CODE IS " + response.code())
                    }
                }
            }

        })
    }
}
