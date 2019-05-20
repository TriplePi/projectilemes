package com.triplepi.projectilemes.presentation

import android.os.Build
import android.support.annotation.RequiresApi
import com.triplepi.projectilemes.domain.interactors.LoadScheduleUseCase
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView
import com.triplepi.projectilemes.ui.adapters.ScheduleAdapter
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO
import java.time.Duration

interface ScheduleView : MvpView {

    var scheduleList: List<ScheduleAdapter.ScheduleItem>
    fun showMainMenuScreen()


}

class SchedulePresenter(view: ScheduleView) : MvpPresenter<ScheduleView>(view) {

    fun onBackButtonClicked() {
        view.showMainMenuScreen()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadSchedule() {
        LoadScheduleUseCase().execute { list -> view.scheduleList = list.map { x -> converDTOToScheduleItem(x) } }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun converDTOToScheduleItem(dto: ScheduleItemDTO?): ScheduleAdapter.ScheduleItem {
        val duration: String = Duration.between(dto!!.StartDate, dto.FinishDate).toHours().toString()
        return ScheduleAdapter.ScheduleItem(
            operation = dto.Operation!!.Name.orEmpty()
            , product = dto.Operation.ProductName.orEmpty()
            , quantity = dto.Batch!!.Quantity.toString()
            , order = dto.Batch.OrderNumber.orEmpty()
            , startDate = dto.StartDate.toString()
            , endDate = dto.FinishDate.toString()
            , duration = duration
        )
    }

}