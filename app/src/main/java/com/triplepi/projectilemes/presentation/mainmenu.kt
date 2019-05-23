package com.triplepi.projectilemes.presentation

import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.triplepi.projectilemes.data.network.dto.*
import com.triplepi.projectilemes.domain.interactors.LoadScheduleUseCase
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView
import com.triplepi.projectilemes.domain.interactors.StageScheduleItemActionUseCase
import java.time.LocalDateTime

interface MainMenuView : MvpView {
    fun showScheduleScreen()
    fun fillCurrentScheduleItem()

    val stageButton: Button
    val startAdjustmentButton: Button
    val acceptToWorkButton: Button
    val pauseTaskButton: Button
    val continueTaskButton: Button
    val doneTaskButton: Button
    val cannotAcceptButton:Button

    var product: String
    var operation: String
    var amount: String

    val processed: Int
    val quarantine: Int

    var scheduleItemDTO: ScheduleItemDTO?
    val quarantineCause: Spinner
    val status: TextView
}

class MainMenuPresenter(view: MainMenuView) : MvpPresenter<MainMenuView>(view) {

    fun onScheduleButtonClicked() {
//        to schedule activity
        view.showScheduleScreen()
    }

    fun onCallButtonClicked() {}
    fun onBarcodeButtonClicked() {
//        scan barcode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onStageButtonClicked() {
        if (view.processed + view.quarantine > view.amount.toInt())
            return
        view.amount = (view.amount.toInt() - (view.processed + view.quarantine)).toString()

        val progress = ScheduleItemProgressIM(
            Processed = ScheduleItemProgressProcessedIM(Count = view.processed),
            Quarantine = ScheduleItemProgressQuarantineIM(
                Count = view.quarantine,
                Comment = view.quarantineCause.selectedItem.toString()
            )
        )
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO?.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                Progress = progress,
                action = ScheduleItemActionIM.Action.executePartial
            )
        ).execute { }
        view.stageButton.isEnabled = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onStartAdjustmentsButtonClicked() {

        StageScheduleItemActionUseCase(
            view.scheduleItemDTO?.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.start
            )
        ).execute { }
        view.cannotAcceptButton.isEnabled = false
        view.status.text = "Наладка"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAcceptToWorkButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO?.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.startExecution
            )
        ).execute { }
        view.startAdjustmentButton.isEnabled = false
        view.acceptToWorkButton.isEnabled = false
        view.pauseTaskButton.isEnabled = true
        view.doneTaskButton.isEnabled = true
        view.stageButton.isEnabled = true
        view.cannotAcceptButton.isEnabled = false
        view.status.text = "Принята в работу"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onPauseTaskButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO?.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.suspend
            )
        ).execute { }
        view.pauseTaskButton.isEnabled = false
        view.continueTaskButton.isEnabled = true
        view.stageButton.isEnabled = false
        view.status.text = "Приостановлена"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onContinueTaskButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO?.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.resume
            )
        ).execute { }
        view.pauseTaskButton.isEnabled = true
        view.doneTaskButton.isEnabled = true
        view.stageButton.isEnabled = true
        view.continueTaskButton.isEnabled = true
        view.status.text = "Принята в работу"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDoneTaskButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO?.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.finish
            )
        ).execute { }
        view.stageButton.isEnabled = false
        view.startAdjustmentButton.isEnabled = false
        view.acceptToWorkButton.isEnabled = false
        view.pauseTaskButton.isEnabled = false
        view.continueTaskButton.isEnabled = false
        view.doneTaskButton.isEnabled = false
        view.status.text = "Завершена"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fillCurrentOperation() {
        LoadScheduleUseCase().execute { x ->
            if (x.isNotEmpty())
                view.scheduleItemDTO = x[0]
            else view.scheduleItemDTO = ScheduleItemDTO()
        }
    }
}