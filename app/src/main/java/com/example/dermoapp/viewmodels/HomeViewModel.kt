package com.example.dermoapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dermoapp.R
import com.example.dermoapp.models.User
import com.example.dermoapp.repositories.UserRepository

class HomeViewModel(val application: Application): ViewModel() {
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

    fun updateCity(city: String, onSuccess: (user: User) -> Unit) {
        setLoading(true)
        UserRepository.updateCity(application, city, { user ->
            onSuccess(user)
        }, {error -> _errorMssg.value = error.mssg
        }, { _errorMssg.value = application.resources.getString(R.string.network_error)
        }, { setLoading(false)
        })
    }

    fun updateUserDetail(user: User, onSuccess: (user: User) -> Unit) {
        setLoading(true)
        UserRepository.updateUserDetail(application, user, { user ->
            onSuccess(user)
        }, {error -> _errorMssg.value = error.mssg
        }, { _errorMssg.value = application.resources.getString(R.string.network_error)
        }, { setLoading(false)
        })
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(application) as T
    }
}