package com.triplepi.projectilemes.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.SignInPresenter
import com.triplepi.projectilemes.presentation.SignInView

class SignInActivity : MvpActivity<SignInView, SignInPresenter>(), SignInView {

    private val signInButton: Button by bind(R.id.sign_in)
    private val passwordInput: EditText by bind(R.id.release)

    override val password: String
        get() = passwordInput.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signin)

        signInButton.setOnClickListener { presenter.onSignInButtonClicked() }
    }

    override fun showMainMenuScreen() {

        val intent = Intent(this@SignInActivity, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showError() {
        Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show()
    }

    override fun createPresenter() = SignInPresenter(this)
}
