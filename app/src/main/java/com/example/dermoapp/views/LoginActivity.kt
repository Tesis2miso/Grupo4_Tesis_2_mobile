package com.example.dermoapp.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivityLoginBinding
import com.example.dermoapp.models.UserLogin
import com.example.dermoapp.utils.SharedPreferencesManager
import com.example.dermoapp.viewmodels.LoginViewModel
import com.example.dermoapp.viewmodels.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var btnLogin: Button
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var formLogin: ScrollView
    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory(this.application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        emailInput = binding.emailInputLogin
        passwordInput = binding.passwordInputLogin
        passwordInputLayout = binding.passwordInputLoginLayout
        btnLogin = binding.btnLogin
        progressIndicator = binding.progressIndicator
        formLogin = binding.formLogin

        btnLogin.setOnClickListener { submitForm() }


        viewModel.loading.observe(this) { loading ->
            if(loading) {
                progressIndicator.visibility = View.VISIBLE
                formLogin.visibility = View.GONE
                btnLogin.visibility = View.GONE
            } else {
                progressIndicator.visibility = View.GONE
                formLogin.visibility = View.VISIBLE
                btnLogin.visibility = View.VISIBLE
            }
        }

        viewModel.errorMssg.observe(this) { mssg ->
            if(mssg != null) {
                openDialog(mssg)
            }
        }

        passwordInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(hasCapsOn(passwordInput.text)) {
                    passwordInputLayout.helperText = getString(R.string.mayus_field)
                } else {
                    passwordInputLayout.helperText = ""
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!viewModel.loading.value!!) {
            finish()
        }
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!viewModel.loading.value!!) {
            super.onBackPressed()
        }
    }

    private fun clearErrors() {
        emailInput.error = null
        passwordInput.error = null
    }

    fun hasCapsOn(text: Editable?): Boolean{
        if (text == null){
            return false
        }
        if(text.isNotEmpty()){
            return text.last().isUpperCase()
        }
        return false
    }

    private fun submitForm() {
        clearErrors()
        var userLogin = UserLogin(
            emailInput.text.toString(),
            passwordInput.text.toString(),
            null
        )
        var error = false

        if(userLogin.email == null || userLogin.email!!.trim() == "") {
            emailInput.error = getString(R.string.empty_field)
            error = true
        }

        if(userLogin.password == null || userLogin.password!!.trim() == "") {
            passwordInput.error = getString(R.string.empty_field)
            error = true
        }

        if(!error) {
            viewModel.login(userLogin) { user ->
                onUserLogged(user)
            }
        }
    }

    private fun onUserLogged(userLogin: UserLogin) {
        SharedPreferencesManager(this).saveStringPreference(
            SharedPreferencesManager.USER_TOKEN,
            userLogin.token!!
        )
        val email = emailInput.text.toString()
        SharedPreferencesManager(this).saveStringPreference(
            SharedPreferencesManager.USER_EMAIL,
            email
        )

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user_email", email)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun openDialog(mssg: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.error)
            .setMessage(mssg)
            .setNeutralButton(R.string.ok) { _, _ ->
                viewModel.clearError()
            }.show()
    }
}