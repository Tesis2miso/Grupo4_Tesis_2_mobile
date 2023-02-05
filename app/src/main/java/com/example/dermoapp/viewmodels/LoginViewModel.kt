package com.example.dermoapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.dermoapp.R
import com.example.dermoapp.models.UserLogin
import com.example.dermoapp.repositories.UserRepository


class LoginViewModel(val application: Application): ViewModel() {
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

    fun login(userLogin: UserLogin, onSuccess: (userLogin: UserLogin) -> Unit) {
        setLoading(true)
        UserRepository.loginUser(userLogin, { user ->
            onSuccess(user)
        }, { error -> _errorMssg.value = error.mssg
        }, { _errorMssg.value = application.resources.getString(R.string.network_error)
        }, { setLoading(false)
        })
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(application) as T
    }
}