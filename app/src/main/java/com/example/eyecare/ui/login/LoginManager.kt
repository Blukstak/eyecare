package com.example.eyecare.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eyecare.data.model.LoginResponseBody
import com.example.eyecare.data.model.SessionData
import com.example.eyecare.data.model.SimpleResult
import com.example.eyecare.data.network.ApiService
import com.example.eyecare.data.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

//Service that has STATE, not stateless
object LoginManager {
    private val authRepository: AuthRepository by lazy {
        AuthRepository(createApiService())
    }

    private val _lastLoginResult = MutableLiveData<SimpleResult>()
    val lastLoginResult: LiveData<SimpleResult> get() = _lastLoginResult
    private var _sessionToken: String = ""
    private var _userName: String? = null
    private var _userEmail: String? = null

    fun IsLoggedIn(): Boolean {
        return _sessionToken.isNotEmpty()
    }

    fun getUserName(): String? {
        return _userName
    }

    fun getUserEmail(): String? {
        return _userEmail
    }

    fun init(application: Application) {
        // Perform any necessary initialization here
    }

    fun loginUser(user: String, alias: String, pass: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.loginUser(user, alias, pass)
            if (result.isSuccess) {
                val data = result.data as LoginResponseBody
                val sessionData = SessionData(user = user, alias = alias, token = data.data.token)
                saveSessionData(sessionData)
                _userName = alias
                _userEmail = user
                val userInfoResult = getUserInfo()
                if (userInfoResult.isSuccess) {
                    Log.d("LoginManager", "User info retrieved successfully")
                    _lastLoginResult.postValue(SimpleResult(isSuccess = true, errorMessage = ""))
                } else {
                    Log.e("LoginManager", "Failed to get user info, logging out user")
                    logoutUser()
                    _lastLoginResult.postValue(SimpleResult(isSuccess = false, errorCode = 2, errorMessage = "Failed to Get User Info. Logging Out User"))
                }
            } else {
                Log.e("LoginManager", "Login failed: ${result.errorMessage}")
                _lastLoginResult.postValue(result)
            }
        }
    }

    private fun saveSessionData(session: SessionData) {
        // Save session data locally
        _sessionToken = session.token
        Log.d("LoginManager", "Session data saved: ${session.token}")
    }

    private suspend fun getUserInfo(): SimpleResult {
        // Implement the logic to get user info
        // Return SimpleResult based on the success or failure of the operation
        Log.d("LoginManager", "Retrieving user info")
        return SimpleResult(isSuccess = true, errorMessage = "")
    }

    fun logoutUser() {
        // Implement the logic to log out the user
        _sessionToken = ""
        _userName = null
        _userEmail = null
        _lastLoginResult.postValue(SimpleResult(isSuccess = false, errorMessage = "User logged out"))
        Log.d("LoginManager", "User logged out")
    }

    private fun createApiService(): ApiService {
        // Create the HttpLoggingInterceptor for logging HTTP request/response data
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Build the OkHttpClient and add the logging interceptor
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add the logging interceptor to the client
            .build()

        // Define the development base URL
        val developmentBaseUrl = "https://dev.mdeyecare.com/api/v1/"

        // Build and return the Retrofit instance with the OkHttpClient
        return Retrofit.Builder()
            .baseUrl(developmentBaseUrl) // Set the base URL
            .client(okHttpClient) // Attach the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // Converter factory for JSON serialization/deserialization
            .build()
            .create(ApiService::class.java) // Create the ApiService implementation
    }
}