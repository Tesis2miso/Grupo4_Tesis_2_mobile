package com.example.dermoapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dermoapp.R
import com.example.dermoapp.models.Consult
import com.example.dermoapp.repositories.ConsultRepository

class UpdateConsultViewModel(val application: Application) : ViewModel() {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _errorMssg: MutableLiveData<String?> = MutableLiveData(null)
    val errorMssg: LiveData<String?> get() = _errorMssg

    private fun setLoading(loading: Boolean) {
        _loading.value = loading
    }

    fun updateConsult(consultId: Int, status: Int, onSuccess: (consult: Consult) -> Unit) {
        setLoading(true)
        ConsultRepository.updateConsult(consultId, status, application, { response_consult ->
            onSuccess(response_consult)
        }, { error -> _errorMssg.value = error.mssg
        }, { _errorMssg.value = application.resources.getString(R.string.network_error)
        }, { setLoading(false)
        })
    }
}

@Suppress("UNCHECKED_CAST")
class UpdateConsultViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpdateConsultViewModel(application) as T
    }
}