package com.triplepi.projectilemes.presentation

import android.widget.Spinner
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.domain.interactors.SignInUseCase
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView

interface SignInView : MvpView {
    val password: String

    val workCenter: Spinner
    val username: Spinner

    fun showMainMenuScreen()

    fun showQRScanScreen()

    fun showError()
}

class SignInPresenter(view: SignInView) : MvpPresenter<SignInView>(view) {

    fun onSignInButtonClicked() {
        if (view.password in listOf("a", "b", "c")) {
            App.INSTANCE.workCenterID = App.INSTANCE.workCenters[view.workCenter.selectedItem]!!
            view.showMainMenuScreen()
        } else {
            view.showError()
        }
//        SignInUseCase("", view.password).execute { signIn: Boolean ->
//
//            if (signIn) {
//                view.showMainMenuScreen()
//            } else {
//                view.showError()
//            }
//        }
    }
}

// view ---- (trigger event) ---> presenter --- (run usecase) ---- ( use case return data ) ----> peresenter ----- (view) ---> view
// todo add stetho dependency
// todo room