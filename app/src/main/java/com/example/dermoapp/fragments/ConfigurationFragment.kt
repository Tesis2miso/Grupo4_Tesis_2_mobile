package com.example.dermoapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.dermoapp.R
import com.example.dermoapp.databinding.FragmentConfigurationBinding
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

    companion object {
        @JvmStatic
        fun newInstance() =
            ConfigurationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}