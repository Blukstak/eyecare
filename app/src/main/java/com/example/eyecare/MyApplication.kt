package com.example.eyecare

import android.app.Application
import com.example.eyecare.data.network.ApiService
import com.example.eyecare.ui.login.LoginManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    val loginManager: LoginManager by lazy {
        LoginManager
    }

    val apiService: ApiService by lazy {
        createApiService()
    }

    val settings: Settings by lazy {
        Settings()
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize LoginManager if needed
        loginManager.init(this)
    }

    private fun createApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

// Define the Settings class
class Settings {
    var userName: String = "Pablo Furia"
    var enableShutterSound: Boolean = false
    var enableGuide: Boolean = true
    var enableAutoFill: Boolean = false
    var advancedCamera: Boolean = false
    // Add other settings as needed
}