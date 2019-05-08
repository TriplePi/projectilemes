package com.triplepi.projectilemes.presentation

import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView
import io.swagger.client.models.OperationModeDTO

interface MainMenuView : MvpView {
    val currentOperation: OperationModeDTO

    fun showScheduleScreen()
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
    fun onCancelButtonClicked() {}
    fun onStartAdjustmentsButtonClicked() {}
    fun onAcceptToWorkButtonClicked() {}
    fun onPauseTaskButtonClicked() {}
    fun onContinueTaskButtonClicked() {}
    fun onDoneTaskButtonClicked() {}

}