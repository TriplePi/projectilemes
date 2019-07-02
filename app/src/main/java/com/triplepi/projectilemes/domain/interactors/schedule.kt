package com.triplepi.projectilemes.domain.interactors

import android.os.Build
import android.support.annotation.RequiresApi
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.joda.time.format.DateTimeFormat

class LoadScheduleUseCase(private val workCenterId: Long) : UseCase<List<ScheduleItemDTO>>() {

    private val api = App.INSTANCE.api

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun run(): List<ScheduleItemDTO> {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SSZ")
        val begin = formatter.print(formatter.parseDateTime("2019-01-30T09:52:00+0400"))
        val end = formatter.print(formatter.parseDateTime("2019-01-30T09:52:00+0400").plusHours(8))
        return api.getSchedule(workCenterId = workCenterId, begin = begin, end = end).execute().body().orEmpty()
//        val gson = Gson()
//        val json = App.INSTANCE.resources.assets.open("schedule.json").bufferedReader().use { it.readText() }
//        val reader = JsonReader(StringReader(json))
//        val type = object : TypeToken<List<ScheduleItemDTO>>() {}.type
//        val schedule = (gson.fromJson(reader, type) as List<ScheduleItemDTO>).filter{ x -> x.WorkCenter?.Id == App.INSTANCE.workCenterID.toLong()}
//        App.INSTANCE.schedule = schedule.toMutableList()
//        return schedule
    }


}