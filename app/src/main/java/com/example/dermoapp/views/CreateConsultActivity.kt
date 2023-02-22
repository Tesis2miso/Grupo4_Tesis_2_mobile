package com.example.dermoapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermoapp.R
import com.example.dermoapp.daos.CreateConsultDAO
import com.example.dermoapp.databinding.ActivityCreateConsultBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class CreateConsultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateConsultBinding
    private lateinit var injuryType: TextInputEditText
    private lateinit var shape: TextInputEditText
    private lateinit var injuryCount: TextInputEditText
    private lateinit var distribution: TextInputEditText
    private lateinit var color: TextInputEditText
    private lateinit var automatic: SwitchMaterial


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateConsultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        injuryType = binding.injuryType
        shape = binding.shape
        injuryCount = binding.injuryCount
        distribution = binding.distribution
        color = binding.color
        automatic = binding.automatic
        binding.btnCreate.setOnClickListener {
            submitForm()
        }
    }

    fun submitForm() {
        clearErrors()
        var error = false
        val consult = CreateConsultDAO(
            automatic.isSelected, color.text.toString(),
            distribution.text.toString(),
            if(injuryCount.text.toString().trim() != "") Integer.parseInt(injuryCount.text.toString()) else null,
            injuryType.text.toString(), null,
            shape.text.toString()
        )

        if(consult.color.trim() == "") {
            color.error = getString(R.string.empty_field)
            error = true
        }

        if(consult.distribution.trim() == "") {
            distribution.error = getString(R.string.empty_field)
            error = true
        }

        if(consult.injuries_count == null) {
            injuryCount.error = getString(R.string.empty_field)
            error = true
        }

        if(consult.injury_type.trim() == "") {
            injuryType.error = getString(R.string.empty_field)
            error = true
        }

        if(consult.shape.trim() == "") {
            shape.error = getString(R.string.empty_field)
            error = true
        }
    }

    private fun clearErrors() {
        injuryType.error = null
        shape.error = null
        injuryCount.error = null
        distribution.error = null
        color.error = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}