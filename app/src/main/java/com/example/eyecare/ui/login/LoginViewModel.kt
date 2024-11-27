package com.example.eyecare.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eyecare.data.model.SimpleResult

class LoginViewModel(private val loginManager: LoginManager) : ViewModel() {

    fun loginUser(user: String, alias: String, pass: String) {
        loginManager.loginUser(user, alias, pass)
    }
}