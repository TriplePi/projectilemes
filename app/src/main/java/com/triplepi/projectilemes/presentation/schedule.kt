package com.triplepi.projectilemes.presentation

import android.os.Build
import android.support.annotation.RequiresApi
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.domain.interactors.LoadScheduleUseCase
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView
import com.triplepi.projectilemes.ui.adapters.ScheduleAdapter
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.LocalDateTime

interface ScheduleView : MvpView {

    var scheduleList: MutableList<ScheduleAdapter.ScheduleItem>
    fun showMainMenuScreen()


}

class SchedulePresenter(view: ScheduleView) : MvpPresenter<ScheduleView>(view) {

    fun onBackButtonClicked() {
        view.showMainMenuScreen()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadSchedule() {
        view.scheduleList = (App.INSTANCE.schedule.map { x->convertDtoToScheduleItem(x) }).toMutableList()
//        LoadScheduleUseCase().execute { list -> view.scheduleList = list.map { x -> convertDtoToScheduleItem(x) } }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDtoToScheduleItem(dto: ScheduleItemDTO?): ScheduleAdapter.ScheduleItem {

        return if (dto != null) {
            val duration = Duration(DateTime(dto.FinishDate!!), DateTime(dto.StartDate!!))
            ScheduleAdapter.ScheduleItem(
                operation = dto.Operation!!.Name.orEmpty()
                , product = dto.Operation.ProductName.orEmpty()
                , quantity = dto.Batch!!.Quantity.toString()
                , order = dto.Batch.OrderNumber.orEmpty()
                , startDate = DateTime(dto.StartDate).hourOfDay.toString()+":"+DateTime(dto.StartDate).minuteOfHour.toString()
                , endDate = DateTime(dto.FinishDate).hourOfDay.toString()+":"+DateTime(dto.FinishDate).minuteOfHour.toString()
                , duration = duration.standardHours.toString()+":"+duration.standardMinutes.toString().subSequence(1,3)
            )
        } else ScheduleAdapter.ScheduleItem(
            operation = "",
            duration = "",
            endDate = "",
            startDate = "",
            order = "",
            quantity = "",
            product = ""
        )
    }
}