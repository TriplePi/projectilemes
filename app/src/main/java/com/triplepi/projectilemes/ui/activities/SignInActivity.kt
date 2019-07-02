package com.triplepi.projectilemes.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.*
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.data.network.dto.UserDataDto
import com.triplepi.projectilemes.data.network.dto.WorkCenterDTO
import com.triplepi.projectilemes.domain.interactors.LoadUsersUseCase
import com.triplepi.projectilemes.domain.interactors.LoadWorkcentersUseCase
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.SignInPresenter
import com.triplepi.projectilemes.presentation.SignInView


class SignInActivity : MvpActivity<SignInView, SignInPresenter>(), SignInView {
    override val users: ArrayList<UserDataDto> = ArrayList()
    override val workCenters: ArrayList<WorkCenterDTO> = ArrayList()

    private val signInButton: Button by bind(com.triplepi.projectilemes.R.id.sign_in)
    private val passwordInput: EditText by bind(com.triplepi.projectilemes.R.id.password)
    private val qrButton: Button by bind(com.triplepi.projectilemes.R.id.qr_button)
    override val workCenter: Spinner by bind(com.triplepi.projectilemes.R.id.workcenter)
    override val username: Spinner by bind(com.triplepi.projectilemes.R.id.username)

    override val password: String
        get() = passwordInput.text.toString()

    @RequiresApi(Build.VERSION_CODES.N)
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
        val usernameList = ArrayList<String>()
        LoadUsersUseCase().execute { x -> x.forEach { y -> users.add(y) } }
        users.forEach{z->usernameList.add(z.name)}
        val usernameAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, usernameList
        )
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        LoadWorkcentersUseCase().execute { x -> x.forEach { y -> workCenters.add(y) } }
        username.adapter = usernameAdapter
        val workcenterAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, workCenters.map { x -> x.Name }
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
