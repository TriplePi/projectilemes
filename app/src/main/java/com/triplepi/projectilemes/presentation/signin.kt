package com.triplepi.projectilemes.presentation

import com.triplepi.projectilemes.domain.interactors.SignInUseCase
import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView

interface SignInView: MvpView {
    val password: String

    fun showMainMenuScreen()

    fun showError()
}

class SignInPresenter(view: SignInView) : MvpPresenter<SignInView>(view) {

    fun onSignInButtonClicked() {

        SignInUseCase("", view.password).execute { signIn: Boolean ->

            if (signIn) {
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