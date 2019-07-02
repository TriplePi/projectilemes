package com.triplepi.projectilemes.ui.activities

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.data.network.dto.ScheduleItemDTO
import com.triplepi.projectilemes.data.network.dto.ScheduleItemWorkCenterDTO
import com.triplepi.projectilemes.domain.interactors.LoadScheduleUseCase
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.MainMenuPresenter
import com.triplepi.projectilemes.presentation.MainMenuView
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
    override val amountView: TextView by bind(R.id.amount_value)
    override val quarantineCause: Spinner by bind(R.id.rejection_cause)
    override val processed: Int
        get() {
            if (processedView.text.toString() == "")
                return 0
            return processedView.text.toString().toInt()
        }
    override val quarantine: Int
        get() {
            if (quarantineView.text.toString() == "")
                return 0
            return quarantineView.text.toString().toInt()
        }

    override val status: TextView by bind(R.id.operation_status_label)

    override var product: String = ""
        get() = productView.text as String

    override var operation: String = ""
        get() = operationView.text as String

    override var amount: String
        get() = amountView.text as String
        set(value) {
            amountView.text = value
        }


    override var scheduleItemDTO: ScheduleItemDTO? = null
    override var scheduleItemWorkCenterDTO: ScheduleItemWorkCenterDTO? = null

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
        cannotAcceptButton.setOnClickListener { presenter.onRejectButtonClicked() }


        stageButton.isEnabled = false
        startAdjustmentButton.isEnabled = true
        acceptToWorkButton.isEnabled = true
        pauseTaskButton.isEnabled = false
        continueTaskButton.isEnabled = false
        doneTaskButton.isEnabled = false
        status.text = "Новая"
        LoadScheduleUseCase(workCenterId = App.INSTANCE.workCenterID.toLong()).execute { }
        fillSpinner()
        presenter.fillCurrentOperation()
        fillCurrentScheduleItem()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun showCallPopup() {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.call_popup, null)
        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.elevation = 10.0F
        val slideIn = Slide()
        slideIn.slideEdge = Gravity.TOP
        popupWindow.enterTransition = slideIn
        val slideOut = Slide()
        slideOut.slideEdge = Gravity.END
        popupWindow.exitTransition = slideOut


        val cancelPopupButton = view.findViewById<Button>(R.id.popup_back)
        val buttons: List<Button> = listOf(
            view.findViewById(R.id.call_master),
            view.findViewById(R.id.call_service),
            view.findViewById(R.id.call_technologist),
            view.findViewById(R.id.call_medic),
            view.findViewById(R.id.call_otk),
            view.findViewById(R.id.call_adjuster)
        )


        var message = ""
        buttons.forEach { x ->
            x.setOnClickListener { message = "Вызов отправлен";popupWindow.dismiss() }

            cancelPopupButton.setOnClickListener {
                popupWindow.dismiss()
            }

            popupWindow.setOnDismissListener {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
            TransitionManager.beginDelayedTransition(findViewById(R.id.main_menu))
            popupWindow.showAtLocation(
                findViewById(R.id.main_menu), // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun showRejectPopup() {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.reject_popup, null)
        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.elevation = 10.0F
        val slideIn = Slide()
        slideIn.slideEdge = Gravity.TOP
        popupWindow.enterTransition = slideIn
        val slideOut = Slide()
        slideOut.slideEdge = Gravity.END
        popupWindow.exitTransition = slideOut


        val cancelPopupButton = view.findViewById<Button>(R.id.cancel_reject)
        val buttons: List<Button> = listOf(
            view.findViewById(R.id.wc_broken),
            view.findViewById(R.id.no_resource),
            view.findViewById(R.id.no_tool)
        )


        buttons.forEach { x ->
            x.setOnClickListener { presenter.onRejectionCauseButtonClicked();popupWindow.dismiss() }

            cancelPopupButton.setOnClickListener {
                popupWindow.dismiss()
            }

            TransitionManager.beginDelayedTransition(findViewById(R.id.main_menu))
            popupWindow.showAtLocation(
                findViewById(R.id.main_menu), // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
        }
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

    override fun showMessage(message:String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}