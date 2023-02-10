package com.example.dermoapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.dermoapp.R
import com.example.dermoapp.databinding.FragmentConfigurationBinding
import com.example.dermoapp.models.User
import com.example.dermoapp.utils.LocaleManager
import com.example.dermoapp.utils.SharedPreferencesManager
import com.example.dermoapp.views.HomeActivity
import com.example.dermoapp.views.MainActivity

class ConfigurationFragment : Fragment() {
    val cities = listOf(
        "Bogotá", "Cali", "Medellin",
        "Barranquilla", "Cartagena", "Bucaramanga",
        "Pereira", "Manizales"
    )
    private lateinit var languages: List<String>
    private var _binding: FragmentConfigurationBinding? = null
    private val binding get() = _binding!!
    private lateinit var cityInput: AutoCompleteTextView
    private lateinit var languageInput: AutoCompleteTextView
    private lateinit var btnSignOut: Button
    private lateinit var btnUpdate: Button
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        val view = binding.root

        cityInput = binding.cityInput
        val cityAdapter = ArrayAdapter(requireActivity().applicationContext, R.layout.input_list_item, cities)
        cityInput.setAdapter(cityAdapter)

        languages = listOf(
            requireActivity().resources?.getString(R.string.spanish)!!,
            requireActivity().resources?.getString(R.string.english)!!,
        )
        languageInput = binding.languageInput
        val languageAdapter = ArrayAdapter(requireActivity().applicationContext, R.layout.input_list_item, languages)
        languageInput.setAdapter(languageAdapter)

        btnSignOut = binding.btnSignOut
        btnSignOut.setOnClickListener {
            SharedPreferencesManager(requireContext()).deleteAllPreferences()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
        btnUpdate = binding.btnUpdate
        btnUpdate.setOnClickListener {
            updateConfig()
        }

        user = SharedPreferencesManager(requireContext()).getModelPreference(
            SharedPreferencesManager.USER, User::class.java
        ) as User
        cityInput.setText(user.city, false)
        languageInput.setText(
            LocaleManager(requireActivity()).getCurrentLocale(), false
        )

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun cityChanged(): Boolean {
        return cityInput.text.toString() != user.city
    }

    fun localeChanged(): Boolean {
        return languageInput.text.toString() != LocaleManager(requireActivity()).getCurrentLocale()
    }

    fun updateConfig() {
        if(cityChanged()) {
            val activity: HomeActivity = requireActivity() as HomeActivity
            val city = cityInput.text.toString()
            activity.viewModel.updateCity(city) { user ->
                this.user = user
                SharedPreferencesManager(requireContext()).saveModelPreference(
                    SharedPreferencesManager.USER,
                    user
                )
                cityInput.setText(user.city, false)
                updateLocaleIfNecessary()
            }
        } else {
            updateLocaleIfNecessary()
        }
    }

    fun updateLocaleIfNecessary() {
        if(localeChanged()) {
            LocaleManager(requireActivity()).setLocale(
                selectedLocaleToAndroidLocale()
            )
            val activity: HomeActivity = requireActivity() as HomeActivity
            activity.recreateFromLocaleChanged()
        }
    }

    fun selectedLocaleToAndroidLocale(): String {
        val selectedLocale = languageInput.text.toString()
        return if(selectedLocale == requireActivity().resources?.getString(R.string.spanish)!!) {
            LocaleManager.SPANISH
        } else {
            LocaleManager.ENGLISH
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConfigurationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}