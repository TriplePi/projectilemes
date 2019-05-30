package com.triplepi.projectilemes

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.triplepi.projectilemes.data.network.Api
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO
import com.triplepi.projectilemes.domain.interactors.LoadScheduleUseCase
import net.danlew.android.joda.JodaTimeAndroid
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class App : Application() {

    init {
        INSTANCE = this
    }

    var workCenterID = 0

    var workCenters = mapOf(
        "ин.389 Vсenter" to 44
        , "ин.439 CHEVALIER" to 45
        , "ин.454 Vсenter" to 49
        , "ин.22 CHEVALIER" to 50
        , "ин.399 CHEVALIER" to 51
        , "ин.161 HARDINGE" to 52
        , "ин.472 CHEVALIER" to 53
        , "ин.471 CHEVALIER" to 54
        , "ин.496 AVRORA" to 55
        , "ин.450 Vсenter" to 56
    )

    var schedule: MutableList<ScheduleItemDTO> = mutableListOf()

    val api: Api by lazy { initApi() }

    private fun initApi(): Api {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_HOST)
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        return retrofit.create(Api::class.java)
    }

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)
    }

    companion object {

        lateinit var INSTANCE: App
    }
}