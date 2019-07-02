package com.triplepi.projectilemes.presentation

import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Spinner
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.data.network.dto.UserDataDto
import com.triplepi.projectilemes.data.network.dto.WorkCenterDTO
import com.triplepi.projectilemes.domain.interactors.SignInUseCase
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView

interface SignInView : MvpView {
    val password: String

    val users: List<UserDataDto>

    val workCenter: Spinner
    val username: Spinner

    fun showMainMenuScreen()

    fun showQRScanScreen()

    fun showError()
    val workCenters: ArrayList<WorkCenterDTO>
}

class SignInPresenter(view: SignInView) : MvpPresenter<SignInView>(view) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun onSignInButtonClicked() {
        SignInUseCase(view.users.stream().filter{x-> x.name == view.username.selectedItem.toString()}.findFirst().get().userName, view.password,"",0).execute { signIn: Boolean ->
            if (signIn) {
                App.INSTANCE.workCenterID = view.workCenters.filter { x-> x.Name == view.workCenter.selectedItem.toString()}.first().Id!!.toInt()
                view.showMainMenuScreen()
            } else {
                view.showError()
            }
        }
    }
}

// view ---- (trigger event) ---> presenter --- (run usecase) ---- ( use case return data ) ----> peresenter ----- (view) ---> view
// todo add stetho dependency
// todo room