package com.triplepi.projectilemes.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.SignInPresenter
import com.triplepi.projectilemes.presentation.SignInView
import net.danlew.android.joda.JodaTimeAndroid

class SignInActivity : MvpActivity<SignInView, SignInPresenter>(), SignInView {

    private val signInButton: Button by bind(R.id.sign_in)
    private val passwordInput: EditText by bind(R.id.password)

    override val password: String
        get() = passwordInput.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signin)

        signInButton.setOnClickListener {
            presenter.onSignInButtonClicked()
        }
    }

    override fun showCongrats() {
        Toast.makeText(this, "You've signed in", Toast.LENGTH_LONG).show()
    }

    override fun showError() {
        Toast.makeText(this, "go fuck your self", Toast.LENGTH_LONG).show()
    }

    override fun createPresenter() = SignInPresenter(this)
}
