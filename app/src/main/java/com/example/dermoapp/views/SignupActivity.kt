package com.example.dermoapp.views

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivitySignupBinding
import com.example.dermoapp.models.User
import com.example.dermoapp.view_models.SignupViewModel
import com.example.dermoapp.view_models.SignupViewModelFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var cityInput: AutoCompleteTextView
    private lateinit var phoneInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordConfirmationInput: TextInputEditText
    private lateinit var birthdayInput: TextInputEditText
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var btnSignup: Button
    private var selectedDate: Date? = null
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var formSignup: ScrollView
    val cities = listOf(
        "Bogotá", "Cali", "Medellin",
        "Barranquilla", "Cartagena", "Bucaramanga",
        "Pereira", "Manizales"
    )
    private val viewModel: SignupViewModel by viewModels { SignupViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nameInput = binding.nameInput
        emailInput = binding.emailInput
        phoneInput = binding.phoneInput
        passwordInput = binding.passwordInput
        passwordConfirmationInput = binding.passwordConfirmationInput
        birthdayInput = binding.birthdayInput
        btnSignup = binding.btnSignup
        progressIndicator = binding.progressIndicator
        formSignup = binding.formSignup

        btnSignup.setOnClickListener { submitForm() }
        cityInput = binding.cityInput
        val adapter = ArrayAdapter(this, R.layout.input_list_item, cities)
        cityInput.setAdapter(adapter)

        datePicker = createDatePicker()
        datePicker.addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<Long?> { selection ->
            val timeZoneUTC = TimeZone.getDefault()
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
            val simpleFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val date = Date(selection + offsetFromUTC)
            selectedDate = date
            birthdayInput.setText(simpleFormat.format(date))
        })

        birthdayInput.setOnClickListener {
            datePicker.show(supportFragmentManager, "")
        }

        viewModel.loading.observe(this) { loading ->
            if(loading) {
                progressIndicator.visibility = View.VISIBLE
                formSignup.visibility = View.GONE
                btnSignup.visibility = View.GONE
            } else {
                progressIndicator.visibility = View.GONE
                formSignup.visibility = View.VISIBLE
                btnSignup.visibility = View.VISIBLE
            }
        }
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

    private fun createDatePicker(): MaterialDatePicker<Long> {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

        return MaterialDatePicker.Builder.datePicker().apply {
            setCalendarConstraints(constraintsBuilder.build())
        }.build()
    }

    private fun clearErrors() {
        nameInput.error = null
        emailInput.error = null
        birthdayInput.error = null
        cityInput.error = null
        phoneInput.error = null
        passwordInput.error = null
        passwordConfirmationInput.error = null
    }

    private fun submitForm() {
        clearErrors()
        val user = User(
            nameInput.text.toString(),
            emailInput.text.toString(),
            selectedDate,
            cityInput.text.toString(),
            phoneInput.text.toString(),
            passwordInput.text.toString(),
        )
        val passwordConfirmation = passwordConfirmationInput.text.toString()
        var error = false

        if(user.name.trim() == "") {
            nameInput.error = getString(R.string.empty_field)
            error = true
        }
        if(user.email.trim() == "") {
            emailInput.error = getString(R.string.empty_field)
            error = true
        }
        if(user.date == null) {
            birthdayInput.error = getString(R.string.empty_field)
            error = true
        }
        if(user.city.trim() == "") {
            cityInput.error = getString(R.string.empty_field)
            error = true
        }
        if(user.phone.trim() == "") {
            phoneInput.error = getString(R.string.empty_field)
            error = true
        }
        if(user.password == null || user.password!!.trim() == "") {
            passwordInput.error = getString(R.string.empty_field)
            error = true
        }
        if(passwordConfirmation.trim() == "" || user.password != passwordConfirmation) {
            passwordConfirmationInput.error = getString(R.string.password_confirmation_does_not_match)
            error = true
        }
        if(!error) {
            viewModel.signUp(user)
        }
    }
}