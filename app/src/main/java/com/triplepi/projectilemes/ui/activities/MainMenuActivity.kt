package com.triplepi.projectilemes.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.MainMenuPresenter
import com.triplepi.projectilemes.presentation.MainMenuView
import io.swagger.client.models.OperationModeDTO

class MainMenuActivity : MvpActivity<MainMenuView, MainMenuPresenter>(), MainMenuView {
    private val scheduleButton: Button by bind(R.id.schedule)
    private val callButton: Button by bind(R.id.call)
    private val barcodeButton: Button by bind(R.id.barcode)
    private val cancelButton: Button by bind(R.id.cancel)
    private val startAdjustmentButton: Button by bind(R.id.start_adjustment)
    private val acceptToWorkButton: Button by bind(R.id.accept_to_work)
    private val pauseTaskButton: Button by bind(R.id.pause_task)
    private val continueTaskButton: Button by bind(R.id.continue_task)
    private val doneTaskButton: Button by bind(R.id.done_task)


    override val currentOperation: OperationModeDTO
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_menu)

        scheduleButton.setOnClickListener { presenter.onScheduleButtonClicked() }
        callButton.setOnClickListener { presenter.onCallButtonClicked() }
        barcodeButton.setOnClickListener { presenter.onBarcodeButtonClicked() }
        cancelButton.setOnClickListener { presenter.onCancelButtonClicked() }
        startAdjustmentButton.setOnClickListener { presenter.onStartAdjustmentsButtonClicked() }
        acceptToWorkButton.setOnClickListener { presenter.onAcceptToWorkButtonClicked() }
        pauseTaskButton.setOnClickListener { presenter.onPauseTaskButtonClicked() }
        continueTaskButton.setOnClickListener { presenter.onContinueTaskButtonClicked() }
        doneTaskButton.setOnClickListener { presenter.onDoneTaskButtonClicked() }
    }

    override fun showScheduleScreen() {
        val intent = Intent(this@MainMenuActivity, null)
        startActivity(intent)
    }

    override fun createPresenter() = MainMenuPresenter(this)
}