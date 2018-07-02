# KotlinRetrofitExample
Retrofit example with Kotlin

Hello everyone. This project works with Retrofit. In the post below I did the same thing but with Java, but now it is in Kotlin.

How I did it? At first you should add in **gradle.build** this things

```xml
compile 'com.squareup.retrofit2:retrofit:2.3.0'
compile 'com.squareup.retrofit2:converter-gson:2.3.0'
```

In Retrofit OkHttp installed automatically. 
The next is using Retrofit. I made server and I have json file it calls **db.json**

After we must to connect and get all info from ther.

How to do it?

1) Make your own api

2) make a class which get all thing from json

3) connection

Let's do it! At first own api.

This is part of code.

```kotlin
interface MessageAPI {

    @GET("employees/{id}")
    fun getUserById(@Path("id") id: Int) : Call<Message>

    @GET("employees/")
    fun json() : Call<JsonElement>
}
```

As you can see we have interface with some methods. Also for this all methods I added annotation. 
Retrofit has some annotation like: **GET**, **POST**, **HEAD**, **Path**, **PUT**, **BODY** and others. But this annotations are very popular.

Okay, let's go back in my code, I have this ```@GET("employees/") ```. But what is it employees? Well just go to my json file and you will see it. In webbrowser it looks like ```http://LOCAL_HOST:3000/employees```.

The next is make a class which get all thing from json.

This is my code:

```kotlin
data class Message(@SerializedName("id") val id: Int,
              @SerializedName("firstname") val firstName: String,
              @SerializedName("lastname") val lastName: String,
              @SerializedName("email") val email: String)
```
The question is why I added annotation, I mean ```@SerializedName("something")```. Well I added it because if you want for example to get info from id then you should do this, add annotations.

And then is connection.

This is part of my code:

```kotlin
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
```

Here as you can see at first I added this

```kotlin
 val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
```
By this line of code I can connect to my server and then convert JSON to normal view.
The next code is to get and to work with my data that's why I added this part of code

```java
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
```

**enqueue(...)** means that everything will be asynchronous. So it will work asynchronous. But if you want that all your "transactions" work synchronous just change **enqueue(...)** on **execute(...)**.

Yesterday I added how to work with **POST** annotation. And now I will talk about it.

So let's start.

As you can see I added **data class** this class can replace two constructors in Java(one without parameter and another with parameters) which I did in project before. Why did I do it? Well, I added it for sending data to my server.

```kotlin
data class Message(@SerializedName("id") val id: Int,
              @SerializedName("firstname") val firstName: String,
              @SerializedName("lastname") val lastName: String,
              @SerializedName("email") val email: String)
```

They are my constructors. Now we can add new annotation for our MessageAPI.

```kotlin
@POST("employees/")
fun NewUser(@Body message: Message) : Call<Message>
```

Cool. The next step is to add in our **MainActivity** new function which will send info about new user. Let's see how it look's like.

```kotlin
fun AddNew() {
        var output = findViewById<TextView>(R.id.Output)
        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val messageAPI = retrofit.create(MessageAPI::class.java)
        val m = Message(8, "Leonel", "Messi", "leomessi@gmail.com")

        val call: Call<Message> = messageAPI.NewUser(m)
        call.enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>?, t: Throwable?) {
                Log.d("FAIL1", t.toString())
            }

            override fun onResponse(call: Call<Message>?, response: Response<Message>?) {
                if(response != null) {
                    if(response.isSuccessful) {
                        output.setText("${response.body()!!.firstName + " " + response.body()!!.email}")
                        Log.d("JSON", response.body()!!.firstName + " " + response.body()!!.email)
                    }
                    else {
                        Toast.makeText(this@MainActivity, "CODE IS " + response.code(), Toast.LENGTH_SHORT).show()
                        Log.d("CODE1", "CODE IS $response.code()")
                    }
                }
            }

        })
    }
```

As you can see nothing new.)

I think that's all. Maybe sometimes I will improve my code and this short doc. Thanks.
