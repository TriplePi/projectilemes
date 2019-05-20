package com.triplepi.projectilemes.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Button
import android.widget.EditText
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.MainMenuPresenter
import com.triplepi.projectilemes.presentation.MainMenuView
import com.triplepi.projectilemes.data.network.dto.OperationModeDTO
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO

class MainMenuActivity : MvpActivity<MainMenuView, MainMenuPresenter>(), MainMenuView {
    private val scheduleButton: Button by bind(R.id.schedule)
    private val callButton: Button by bind(R.id.call)
    private val barcodeButton: Button by bind(R.id.barcode)
    override val stageButton: Button by bind(R.id.stage)
    override val startAdjustmentButton: Button by bind(R.id.start_adjustment)
    override val acceptToWorkButton: Button by bind(R.id.accept_to_work)
    override val pauseTaskButton: Button by bind(R.id.pause_task)
    override val continueTaskButton: Button by bind(R.id.continue_task)
    override val doneTaskButton: Button by bind(R.id.done_task)
    private val processedView: EditText by bind(R.id.processed)
    private val quarantineView: EditText by bind(R.id.quarantine)
    override val processed: Int
        get() = processedView.text.toString().toInt()
    override val quarantine: Int
        get() = quarantineView.text.toString().toInt()


    override val scheduleItemDTO: ScheduleItemDTO
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stageButton.isActivated=false
        startAdjustmentButton.isActivated=true
        acceptToWorkButton.isActivated=false
        pauseTaskButton.isActivated=false
        continueTaskButton.isActivated=false
        doneTaskButton.isActivated=false

        setContentView(R.layout.activity_main_menu)

        scheduleButton.setOnClickListener { presenter.onScheduleButtonClicked() }
        callButton.setOnClickListener { presenter.onCallButtonClicked() }
        barcodeButton.setOnClickListener { presenter.onBarcodeButtonClicked() }
        stageButton.setOnClickListener { presenter.onStageButtonClicked() }
        startAdjustmentButton.setOnClickListener { presenter.onStartAdjustmentsButtonClicked() }
        acceptToWorkButton.setOnClickListener { presenter.onAcceptToWorkButtonClicked() }
        pauseTaskButton.setOnClickListener { presenter.onPauseTaskButtonClicked() }
        continueTaskButton.setOnClickListener { presenter.onContinueTaskButtonClicked() }
        doneTaskButton.setOnClickListener { presenter.onDoneTaskButtonClicked() }
    }

    override fun showScheduleScreen() {
        val intent = Intent(this@MainMenuActivity, ScheduleActivity::class.java)
        startActivity(intent)
    }

    override fun createPresenter() = MainMenuPresenter(this)
}