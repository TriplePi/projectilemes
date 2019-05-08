package com.triplepi.projectilemes.mvp

interface MvpView

abstract class MvpPresenter <T : MvpView>(
    protected val view: T
) {

    open fun onCreated() {}

    open fun onStarted() {}

    open fun onResumed() {}

    open fun onPaused() {}

    open fun onStopped() {}
}