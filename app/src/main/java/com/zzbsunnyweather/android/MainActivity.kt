package com.zzbsunnyweather.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.zip.GZIPOutputStream
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    class App(val name:String,val age:String)

    interface AppService{
        @GET("test.json")
        fun getAppData():Call<List<App>>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAppDataBtn.setOnClickListener {
            val retrofit=Retrofit.Builder()
                .baseUrl("http://10.0.2.2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService=retrofit.create(AppService::class.java)
            appService.getAppData().enqueue(object :Callback<List<App>>{
                override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                    val list=response.body()
                    if (list!=null){
                        for (App in list){
                            Log.d("MainActivity","name is ${App.name}")
                            Log.d("MainActivity","age is ${App.age}")

                        }
                    }
                }

                override fun onFailure(call: Call<List<App>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}
