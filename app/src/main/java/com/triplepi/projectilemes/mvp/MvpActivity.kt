package com.triplepi.projectilemes.mvp

import android.os.Bundle
import com.triplepi.projectilemes.ui.activities.BaseActivity

abstract class MvpActivity <TView : MvpView, TPresenter: MvpPresenter<TView>> : BaseActivity() {

    protected val presenter : TPresenter by lazy { createPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreated()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStarted()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResumed()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPaused()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStopped()
    }

    abstract fun createPresenter() : TPresenter
}