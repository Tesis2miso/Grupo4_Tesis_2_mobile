package com.example.dermoapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivityConsultDetailBinding
import com.example.dermoapp.models.Consult
import com.example.dermoapp.models.Model
import com.squareup.picasso.Picasso

class ConsultDetailActivity : AppCompatActivity() {
    companion object {
        val CONSULT_EXTRA = "CONSULT_EXTRA"
    }

    private lateinit var binding: ActivityConsultDetailBinding
    private lateinit var consult: Consult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val consultAsTring = intent.getStringExtra(ConsultDetailActivity.CONSULT_EXTRA)
        consult = Model.fromGsonString(consultAsTring!!, Consult::class.java) as Consult
        loadConsult()
    }

    fun loadConsult() {
        binding.detailInjuryType.setText(consult.injuryType)
        binding.detailColor.setText(consult.color)
        binding.detailDistribution.setText(consult.distribution)
        binding.detailInjuryCount.setText(consult.injuriesCount.toString())
        binding.detailShape.setText(consult.shape)

        Picasso.get().load(consult.photoUrl).placeholder(R.drawable.profile)
            .into(binding.detailImage)

        if (consult.specialist != null) {
            binding.detailSpecialist.visibility = View.VISIBLE
            binding.specialistName.setText(
                "${consult.specialist!!.name} ${consult.specialist!!.lastName}"
            )
            binding.specialistUsername.setText(consult.specialist!!.username)
        } else if(consult.automatic) {
            binding.detailSpecialist.visibility = View.VISIBLE
            binding.specialistUsername.setText(null)
            binding.specialistName.setText(R.string.detail_automatic)
        }

        if (consult.diagnosis != null) {
            binding.detailDiagnosisHolder.visibility = View.VISIBLE
            binding.detailDiagnosis.setText(consult.diagnosis!!)
        }

        if (consult.automatic) {
            binding.detailAutomatic.setText(getString(R.string.yes))
        } else {
            binding.detailAutomatic.setText(getString(R.string.no))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}