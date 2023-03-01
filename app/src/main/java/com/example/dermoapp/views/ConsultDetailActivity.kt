package com.example.dermoapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivityConsultDetailBinding
import com.example.dermoapp.models.Consult
import com.example.dermoapp.models.Model
import com.example.dermoapp.viewmodels.UpdateConsultViewModel
import com.example.dermoapp.viewmodels.UpdateConsultViewModelFactory
import com.squareup.picasso.Picasso

class ConsultDetailActivity : AppCompatActivity() {
    companion object {
        val CONSULT_EXTRA = "CONSULT_EXTRA"
    }

    private lateinit var binding: ActivityConsultDetailBinding
    private lateinit var consult: Consult

    val viewModel: UpdateConsultViewModel by viewModels {
        UpdateConsultViewModelFactory(application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val consultAsTring = intent.getStringExtra(ConsultDetailActivity.CONSULT_EXTRA)
        consult = Model.fromGsonString(consultAsTring!!, Consult::class.java) as Consult
        loadConsult()

        binding.btnAccept.setOnClickListener {
            acceptDiagnostic()
        }

        binding.btnReject.setOnClickListener {
            rejectDiagnostic()
        }
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

        if (consult.diagnosis != null && consult.status == 0) {
            binding.btnAccept.visibility = View.VISIBLE
            binding.btnReject.visibility = View.VISIBLE
        }

        if (consult.automatic) {
            binding.detailAutomatic.setText(getString(R.string.yes))
        } else {
            binding.detailAutomatic.setText(getString(R.string.no))
        }
    }

    fun acceptDiagnostic(){
        consult.id?.let {
            viewModel.updateConsult(it, 1,  onSuccess = {
                this.finish()
            })
        }
    }

    fun rejectDiagnostic(){
        consult.id?.let {
            viewModel.updateConsult(it, 2,  onSuccess = {
                this.finish()
            })
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