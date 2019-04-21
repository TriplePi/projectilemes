package com.triplepi.projectilemes.mvp

interface MvpView

abstract class MvpPresenter <T : MvpView>(
    protected val view: T
) {

    fun onCreated() {}

    fun onStarted() {}

    fun onResumed() {}

    fun onPaused() {}

    fun onStopped() {}
}