package com.example.dermoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ScrollView
import com.example.dermoapp.R
import com.example.dermoapp.databinding.FragmentAccountBinding
import com.example.dermoapp.models.User
import com.example.dermoapp.utils.SharedPreferencesManager
import com.example.dermoapp.views.HomeActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var cityInput: AutoCompleteTextView
    private lateinit var phoneInput: TextInputEditText
    private lateinit var birthdayInput: TextInputEditText
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var btnUpdate: Button
    private var selectedDate: Date? = null
    private lateinit var formUpdate: ScrollView
    private lateinit var user: User

    val cities = listOf(
        "Bogotá", "Cali", "Medellin",
        "Barranquilla", "Cartagena", "Bucaramanga",
        "Pereira", "Manizales"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = binding.root

        nameInput = binding.nameInput
        emailInput = binding.emailInput
        phoneInput = binding.phoneInput
        birthdayInput = binding.birthdayInput
        btnUpdate = binding.btnUpdate
        formUpdate = binding.formProfile

        btnUpdate.setOnClickListener { submitForm() }
        cityInput = binding.cityInput
        val adapter = ArrayAdapter(requireContext(), R.layout.input_list_item, cities)
        cityInput.setAdapter(adapter)

        datePicker = createDatePicker()
        datePicker.addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<Long?> { selection ->
            val timeZoneUTC = TimeZone.getDefault()
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
            val date = Date(selection + offsetFromUTC)
            selectedDate = date
            birthdayInput.setText(dateToString(date))
        })

        birthdayInput.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "")
        }

        user = SharedPreferencesManager(requireContext()).getModelPreference(
            SharedPreferencesManager.USER, User::class.java
        ) as User

        nameInput.setText(user.name)
        emailInput.setText(user.email)
        phoneInput.setText(user.phone)
        cityInput.setText(user.city, false)
        selectedDate = stringToDate(user.birthDay)
        birthdayInput.setText(selectedDate?.let { dateToString(it) })

        return view
    }

    private fun createDatePicker(): MaterialDatePicker<Long> {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

        return MaterialDatePicker.Builder.datePicker().apply {
            setCalendarConstraints(constraintsBuilder.build())
        }.build()
    }

    private fun dateToString(dateToFormat: Date): String {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return simpleFormat.format(dateToFormat)
    }

    private fun stringToDate(dateString: String?): Date? {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return dateString?.let { simpleFormat.parse(it) }
    }

    private fun clearErrors() {
        nameInput.error = null
        emailInput.error = null
        birthdayInput.error = null
        cityInput.error = null
        phoneInput.error = null
    }

    private fun submitForm() {
        val activity: HomeActivity = requireActivity() as HomeActivity
        clearErrors()
        val userLocal = User(
            nameInput.text.toString(),
            emailInput.text.toString(),
            if(selectedDate == null) null else dateToString(selectedDate!!),
            cityInput.text.toString(),
            phoneInput.text.toString(),
            null,
            id=user.id
        )
        var error = false

        if(userLocal.name.trim() == "") {
            nameInput.error = getString(R.string.empty_field)
            error = true
        }
        if(userLocal.email.trim() == "") {
            emailInput.error = getString(R.string.empty_field)
            error = true
        }
        if(userLocal.birthDay == null) {
            birthdayInput.error = getString(R.string.empty_field)
            error = true
        }
        if(userLocal.city.trim() == "") {
            cityInput.error = getString(R.string.empty_field)
            error = true
        }
        if(userLocal.phone.trim() == "") {
            phoneInput.error = getString(R.string.empty_field)
            error = true
        }
        if(!error) {
            activity.viewModel.updateUserDetail(userLocal) { user ->
                onUserUpdated(user)
            }
        }
    }

    private fun onUserUpdated(user: User) {
        SharedPreferencesManager(requireContext()).saveModelPreference(
            SharedPreferencesManager.USER,
            user
        )
    }

    private fun openDialog(mssg: String) {
        val activity: HomeActivity = requireActivity() as HomeActivity
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(mssg)
            .setNeutralButton(R.string.ok) { _, _ ->
                activity.viewModel.clearError()
            }.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}