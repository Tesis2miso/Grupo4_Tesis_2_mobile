package com.example.dermoapp.view_models

import androidx.lifecycle.*
import com.example.dermoapp.models.User
import kotlinx.coroutines.launch

class SignupViewModel: ViewModel() {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    init {
        viewModelScope.launch {
        }
    }

    fun setLoading(loading: Boolean) {
        _loading.value = loading
    }

    fun signUp(user: User) {
    }
}

@Suppress("UNCHECKED_CAST")
class SignupViewModelFactory: ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel() as T
    }
}