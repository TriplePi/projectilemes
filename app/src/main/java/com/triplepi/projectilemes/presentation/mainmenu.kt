package com.triplepi.projectilemes.presentation

import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Button
import android.widget.EditText
import com.triplepi.projectilemes.data.network.dto.*
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView
import com.triplepi.projectilemes.domain.interactors.StageScheduleItemActionUseCase
import java.time.LocalDateTime

interface MainMenuView : MvpView {
    val scheduleItemDTO: ScheduleItemDTO

    fun showScheduleScreen()

    val stageButton: Button
    val startAdjustmentButton: Button
    val acceptToWorkButton: Button
    val pauseTaskButton: Button
    val continueTaskButton: Button
    val doneTaskButton: Button

    val processed: Int
    val quarantine: Int
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
        val progress = ScheduleItemProgressIM(
            Processed = ScheduleItemProgressProcessedIM(Count = view.processed),
            Quarantine = ScheduleItemProgressQuarantineIM(Count = view.quarantine, Comment = "")
        )
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                Progress = progress,
                action = ScheduleItemActionIM.Action.executePartial
            )
        ).execute { }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onStartAdjustmentsButtonClicked() {

        StageScheduleItemActionUseCase(
            view.scheduleItemDTO.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.start
            )
        ).execute { }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAcceptToWorkButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.startExecution
            )
        ).execute { }
        view.startAdjustmentButton.isActivated = false
        view.acceptToWorkButton.isActivated = false
        view.pauseTaskButton.isActivated = true
        view.doneTaskButton.isActivated = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onPauseTaskButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.suspend
            )
        ).execute { }
        view.startAdjustmentButton.isActivated = false
        view.acceptToWorkButton.isActivated = false
        view.pauseTaskButton.isActivated = false
        view.continueTaskButton.isActivated = true
        view.doneTaskButton.isActivated = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onContinueTaskButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.resume
            )
        ).execute { }
        view.startAdjustmentButton.isActivated = false
        view.acceptToWorkButton.isActivated = false
        view.pauseTaskButton.isActivated = true
        view.doneTaskButton.isActivated = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDoneTaskButtonClicked() {
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.finish
            )
        ).execute { }
        view.stageButton.isActivated = false
        view.startAdjustmentButton.isActivated = true
        view.acceptToWorkButton.isActivated = false
        view.pauseTaskButton.isActivated = false
        view.continueTaskButton.isActivated = false
        view.doneTaskButton.isActivated = false
    }

}