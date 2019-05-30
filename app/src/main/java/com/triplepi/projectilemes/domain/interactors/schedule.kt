package com.triplepi.projectilemes.domain.interactors

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.apache.commons.io.IOUtils
import java.io.StringReader

class LoadScheduleUseCase : UseCase<List<ScheduleItemDTO>>() {

    private val api = App.INSTANCE.api

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun run(): List<ScheduleItemDTO> {
//        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SSZ")
//        val begin = formatter.print(formatter.parseDateTime("2019-01-30T09:52:00Z"))
//        val end = formatter.print(formatter.parseDateTime("2019-01-30T09:52:00Z").plusHours(8))
//        return api.getSchedule(minTs = null, begin = begin, end = end).execute().body().orEmpty()
//            .filter { x -> x.WorkCenter?.Id == App.INSTANCE.workCenterID.toLong() }
        val gson = Gson()
        val json = App.INSTANCE.resources.assets.open("schedule.json").bufferedReader().use { it.readText() }
        val reader = JsonReader(StringReader(json))
        val type = object : TypeToken<List<ScheduleItemDTO>>() {}.type
        val schedule = (gson.fromJson(reader, type) as List<ScheduleItemDTO>).filter{ x -> x.WorkCenter?.Id == App.INSTANCE.workCenterID.toLong()}
        App.INSTANCE.schedule = schedule.toMutableList()
        return schedule
    }


}