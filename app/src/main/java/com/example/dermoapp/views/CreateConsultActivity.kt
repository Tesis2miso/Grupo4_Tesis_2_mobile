package com.example.dermoapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dermoapp.R
import com.example.dermoapp.daos.CreateConsultDAO
import com.example.dermoapp.databinding.ActivityCreateConsultBinding
import com.example.dermoapp.viewmodels.CreateConsultViewModel
import com.example.dermoapp.viewmodels.CreateConsultViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class CreateConsultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateConsultBinding
    private lateinit var injuryType: TextInputEditText
    private lateinit var shape: TextInputEditText
    private lateinit var injuryCount: TextInputEditText
    private lateinit var distribution: TextInputEditText
    private lateinit var color: TextInputEditText
    private lateinit var automatic: SwitchMaterial
    private var photoUrl: String? = null
    private lateinit var photoLayout: TextInputLayout
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var createForm: LinearLayout
    private var mStorageRef: StorageReference? = FirebaseStorage.getInstance().getReference();
    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null && result.data != null) {
                val imageUri: Uri? = result.data?.data
                val sd = getFileName(applicationContext, imageUri!!)
                val uploadTask = mStorageRef!!.child("file/$sd").putFile(imageUri)
                uploadTask.addOnSuccessListener {
                    mStorageRef!!.child("file/$sd").downloadUrl.addOnSuccessListener {
                        Picasso.get().load(it).placeholder(R.drawable.profile)
                            .into(binding.photo)
                        photoUrl = it.toString()
                    }.addOnFailureListener {
                        openDialog(getString(R.string.upload_photo_error))
                    }
                }.addOnFailureListener {
                    openDialog(getString(R.string.upload_photo_error))
                }
            }
        }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    )
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    val viewModel: CreateConsultViewModel by viewModels {
        CreateConsultViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateConsultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressIndicator = binding.progressIndicator
        injuryType = binding.injuryType
        shape = binding.shape
        injuryCount = binding.injuryCount
        distribution = binding.distribution
        color = binding.color
        automatic = binding.automatic
        photoLayout = binding.photoLayout
        createForm = binding.createFrame
        binding.btnCreate.setOnClickListener {
            submitForm()
        }
        binding.photo.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            imagePickerActivityResult.launch(galleryIntent)
        }

        viewModel.loading.observe(this) { loading ->
            if (loading) {
                progressIndicator.visibility = View.VISIBLE
                createForm.visibility = View.GONE
            } else {
                progressIndicator.visibility = View.GONE
                createForm.visibility = View.VISIBLE
            }
        }

        viewModel.errorMssg.observe(this) { mssg ->
            if (mssg != null) {
                openDialog(mssg)
            }
        }
    }

    fun submitForm() {
        clearErrors()
        var error = false
        val consult = CreateConsultDAO(
            automatic.isChecked, color.text.toString(),
            distribution.text.toString(),
            if (injuryCount.text.toString()
                    .trim() != ""
            ) Integer.parseInt(injuryCount.text.toString()) else null,
            injuryType.text.toString(), photoUrl,
            shape.text.toString()
        )

        if (consult.color.trim() == "") {
            color.error = getString(R.string.empty_field)
            error = true
        }

        if (consult.distribution.trim() == "") {
            distribution.error = getString(R.string.empty_field)
            error = true
        }

        if (consult.injuries_count == null) {
            injuryCount.error = getString(R.string.empty_field)
            error = true
        }

        if (consult.injury_type.trim() == "") {
            injuryType.error = getString(R.string.empty_field)
            error = true
        }

        if (consult.shape.trim() == "") {
            shape.error = getString(R.string.empty_field)
            error = true
        }

        if (photoUrl == null) {
            photoLayout.error = getString(R.string.empty_field)
            error = true
        }

        if (!error) {
            viewModel.createConsult(consult,  onSuccess = {
                this.finish()
            })
        }
    }

    private fun clearErrors() {
        injuryType.error = null
        shape.error = null
        injuryCount.error = null
        distribution.error = null
        color.error = null
        photoLayout.error = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!viewModel.loading.value!!) {
            super.onBackPressed()
        }
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