package com.triplepi.projectilemes

import android.app.Application
import com.triplepi.projectilemes.data.network.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class App : Application() {

    val api: Api by lazy { initApi() }


    private fun initApi(): Api {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        return retrofit.create(Api::class.java)
    }
}