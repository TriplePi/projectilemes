package com.triplepi.projectilemes.presentation

import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.triplepi.projectilemes.data.network.dto.*
import com.triplepi.projectilemes.domain.interactors.LoadScheduleUseCase
import com.triplepi.projectilemes.domain.interactors.StageScheduleItemActionUseCase
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView
import java.time.LocalDateTime


interface MainMenuView : MvpView {
    fun showScheduleScreen()
    fun fillCurrentScheduleItem()
    fun showCallPopup()

    val amountView: TextView
    val stageButton: Button
    val startAdjustmentButton: Button
    val acceptToWorkButton: Button
    val pauseTaskButton: Button
    val continueTaskButton: Button
    val doneTaskButton: Button
    val cannotAcceptButton: Button

    var product: String
    var operation: String
    var amount: String

    val processed: Int
    val quarantine: Int

    var scheduleItemDTO: ScheduleItemDTO?
    val quarantineCause: Spinner
    val status: TextView
    @TargetApi(value = 26)
    @RequiresApi(value = 23)
    fun showRejectPopup()

    fun showMessage(message: String)
    var scheduleItemWorkCenterDTO: ScheduleItemWorkCenterDTO?
}

class MainMenuPresenter(view: MainMenuView) : MvpPresenter<MainMenuView>(view) {

    fun onScheduleButtonClicked() {
//        to schedule activity
        view.showScheduleScreen()
    }

    fun onCallButtonClicked() {
        view.showCallPopup()
    }

    fun onBarcodeButtonClicked() {
//        scan barcode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onStageButtonClicked() {
        if (view.processed + view.quarantine > view.amount.toInt()) {
            view.showMessage("Некорректное количество")
            return
        }
//        view.amount = (view.amount.toInt() - (view.processed + view.quarantine)).toString()
        view.amountView.text = (view.amount.toInt() - (view.processed + view.quarantine)).toString()

        view.scheduleItemDTO?.Batch?.Quantity =
            view.scheduleItemDTO?.Batch?.Quantity?.minus((view.processed + view.quarantine))
        fillCurrentOperation()
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
        view.amount = view.scheduleItemDTO!!.Batch?.Quantity.toString()
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
        if (view.amountView.text != "0") {
            view.showMessage("Завершите работу над операцией")
            return
        }
        StageScheduleItemActionUseCase(
            view.scheduleItemDTO?.Id!!,
            scheduleItemActionIM = ScheduleItemActionIM(
                Date = LocalDateTime.now(),
                action = ScheduleItemActionIM.Action.finish
            )
        ).execute { }
        view.scheduleItemDTO?.status = ScheduleItemDTO.Status.Finished
//        App.INSTANCE.schedule.removeAt(0)
        view.stageButton.isEnabled = false
        view.startAdjustmentButton.isEnabled = true
        view.acceptToWorkButton.isEnabled = true
        view.pauseTaskButton.isEnabled = false
        view.continueTaskButton.isEnabled = false
        view.doneTaskButton.isEnabled = false
        fillCurrentOperation()
        view.fillCurrentScheduleItem()
        view.status.text = "Завершена"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onRejectButtonClicked() {
        view.showRejectPopup()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onRejectionCauseButtonClicked() {
        view.amountView.text = "0"
        onDoneTaskButtonClicked()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fillCurrentOperation() {
//        view.scheduleItemDTO = App.INSTANCE.schedule[0]
        LoadScheduleUseCase(view.scheduleItemWorkCenterDTO?.Id!!).execute { x ->
            if (x.isNotEmpty())
                view.scheduleItemDTO = x[0]
            else view.showMessage("Закончились задачи")
        }
    }
}