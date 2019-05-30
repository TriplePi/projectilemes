package com.triplepi.projectilemes.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.SignInPresenter
import com.triplepi.projectilemes.presentation.SignInView


class SignInActivity : MvpActivity<SignInView, SignInPresenter>(), SignInView {

    private val api = App.INSTANCE.api

    private val signInButton: Button by bind(com.triplepi.projectilemes.R.id.sign_in)
    private val passwordInput: EditText by bind(com.triplepi.projectilemes.R.id.password)
    private val qrButton: Button by bind(com.triplepi.projectilemes.R.id.qr_button)
    override val workCenter: Spinner by bind(com.triplepi.projectilemes.R.id.workcenter)
    override val username: Spinner by bind(com.triplepi.projectilemes.R.id.username)

    override val password: String
        get() = passwordInput.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.triplepi.projectilemes.R.layout.activity_signin)
        signInButton.setOnClickListener { presenter.onSignInButtonClicked() }
        qrButton.setOnClickListener { showQRScanScreen() }
        fillSpinners()
    }

    override fun showMainMenuScreen() {

        val intent = Intent(this@SignInActivity, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun fillSpinners() {
        val usernameList = listOf("Колотовченко А.И.", "Никитченко В.В.", "Яков Л.Я.")
        val usernameAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, usernameList
        )
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        username.adapter = usernameAdapter

        val workcenterList = App.INSTANCE.workCenters.keys.toMutableList()
        val workcenterAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, workcenterList
        )
        workcenterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        workCenter.adapter = workcenterAdapter
    }

    override fun showQRScanScreen() {
        val intent = Intent(this@SignInActivity, ScanActivity::class.java)
        startActivity(intent)
    }

    override fun showError() {
        Toast.makeText(this, "Некорректные учётные данные", Toast.LENGTH_LONG).show()
    }

    override fun createPresenter() = SignInPresenter(this)
}
