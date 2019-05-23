package com.triplepi.projectilemes.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.*
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.MainMenuPresenter
import com.triplepi.projectilemes.presentation.MainMenuView
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO
import kotlinx.android.synthetic.main.activity_main_menu.*

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
    override val cannotAcceptButton: Button by bind(R.id.cannot)
    private val processedView: EditText by bind(R.id.processed)
    private val quarantineView: EditText by bind(R.id.quarantine)
    private val productView: TextView by bind(R.id.product_value)
    private val operationView: TextView by bind(R.id.operation_value)
    private val amountView: TextView by bind(R.id.amount_value)
    override val quarantineCause: Spinner by bind(R.id.rejection_cause)
    override val processed: Int
        get() = processedView.text.toString().toInt()
    override val quarantine: Int
        get() = quarantineView.text.toString().toInt()

    override val status: TextView by bind(R.id.operation_status_label)

    override var product: String = ""
        get() = productView.text as String

    override var operation: String = ""
        get() = operationView.text as String

    override var amount: String = ""
        get() = amountView.text as String


    override var scheduleItemDTO: ScheduleItemDTO? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        stageButton.isEnabled = false
        startAdjustmentButton.isEnabled = true
        acceptToWorkButton.isEnabled = true
        pauseTaskButton.isEnabled = false
        continueTaskButton.isEnabled = false
        doneTaskButton.isEnabled = false
        status.text = "Новая"
        fillSpinner()
        presenter.fillCurrentOperation()
        fillCurrentScheduleItem()
    }

    override fun showScheduleScreen() {
        val intent = Intent(this@MainMenuActivity, ScheduleActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun fillCurrentScheduleItem() {
        operationView.text = scheduleItemDTO?.Operation?.Name ?: ""
        productView.text = scheduleItemDTO?.Operation?.ProductName ?: ""
        amountView.text = scheduleItemDTO?.Batch?.Quantity.toString()
    }

    override fun createPresenter() = MainMenuPresenter(this)

    fun fillSpinner() {
        val problems = listOf("Брак заготовки", "Неисправность станка", "Неисправность инструмента", "Ошибка")
        val rejectionCauseAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, problems
        )
        rejectionCauseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rejection_cause.adapter = rejectionCauseAdapter
    }
}