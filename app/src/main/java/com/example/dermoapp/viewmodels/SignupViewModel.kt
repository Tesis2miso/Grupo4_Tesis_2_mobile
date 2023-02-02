package com.example.dermoapp.viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.example.dermoapp.R
import com.example.dermoapp.models.User
import com.example.dermoapp.repositories.UserRepository
import com.example.dermoapp.views.SignupActivity
import kotlinx.coroutines.launch


class SignupViewModel(val application: Application): ViewModel() {
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

    fun signUp(user: User, onSuccess: (user: User) -> Unit) {
        setLoading(true)
        UserRepository.createUser(user, { new_user ->
            onSuccess(new_user)
        }, { error -> _errorMssg.value = error.mssg
           }, { _errorMssg.value = application.resources.getString(R.string.network_error)
        }, { setLoading(false)
        })
    }
}

@Suppress("UNCHECKED_CAST")
class SignupViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(application) as T
    }
}