package com.example.dermoapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dermoapp.R
import com.example.dermoapp.daos.ConsultDAO
import com.example.dermoapp.daos.CreateConsultDAO
import com.example.dermoapp.models.Consult

class CreateConsultViewModel(val application: Application) : ViewModel() {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _errorMssg: MutableLiveData<String?> = MutableLiveData(null)
    val errorMssg: LiveData<String?> get() = _errorMssg

    private fun setLoading(loading: Boolean) {
        _loading.value = loading
    }

    fun clearError() {
        _errorMssg.value = null
    }

    fun createConsult(consult: CreateConsultDAO, onSuccess: (consult: Consult) -> Unit) {
        setLoading(true)
    }
}

@Suppress("UNCHECKED_CAST")
class CreateConsultViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateConsultViewModel(application) as T
    }
}