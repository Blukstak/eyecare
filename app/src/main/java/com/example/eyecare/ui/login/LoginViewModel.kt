package com.example.eyecare.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eyecare.data.model.SimpleResult

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<SimpleResult>()
    val loginResult: LiveData<SimpleResult> get() = _loginResult

    fun login(user: String, alias: String, pass: String) {
        LoginManager.loginUser(user, alias, pass)
        LoginManager.lastLoginResult.observeForever { result ->
            _loginResult.postValue(result)
        }
    }
}