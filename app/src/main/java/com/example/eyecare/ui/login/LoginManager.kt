package com.example.eyecare.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eyecare.data.model.SessionData
import com.example.eyecare.data.model.SimpleResult
import com.example.eyecare.data.network.ApiService
import com.example.eyecare.data.network.LoginResponseBody
import com.example.eyecare.data.repository.AuthRepository
import com.example.eyecare.data.repository.UserRepository
import com.example.eyecare.data.repository.ImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object LoginManager {
    private val authRepository: AuthRepository by lazy {
        AuthRepository(createApiService())
    }
    private val userRepository: UserRepository by lazy {
        UserRepository(createApiService())
    }
    private val imageRepository: ImageRepository by lazy {
        ImageRepository(createApiService())
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
        _sessionToken = session.token
        Log.d("LoginManager", "Session data saved: ${session.token}")
    }

    suspend fun getUserInfo(): SimpleResult {
        return userRepository.getUserInfo(_userEmail ?: "", _userName ?: "", _sessionToken)
    }

    suspend fun getUserPatients(): SimpleResult {
        return userRepository.getUserPatients(_userEmail ?: "", _userName ?: "", _sessionToken, 1, 10)
    }

    suspend fun registerPatient(): SimpleResult {
        // Replace with actual data
        val identifier = "identifier"
        val birthDate = System.currentTimeMillis()
        val email = _userEmail ?: ""
        val consentImage = ByteArray(0) // Replace with actual image data
        return userRepository.registerPatient(identifier, birthDate, email, consentImage)
    }

    suspend fun getUserCloudImageInfo(): SimpleResult {
        return imageRepository.getUserCloudImageInfo(_userEmail ?: "", _userName ?: "", _sessionToken, 1, 10)
    }

    suspend fun getImage(): SimpleResult {
        // Replace with actual image ID
        val imageId = "imageId"
        return imageRepository.getImage(imageId, _userEmail ?: "", _userName ?: "", _sessionToken)
    }

    // suspend fun uploadImage(): SimpleResult {
    //     // Replace with actual image data
    //     val image = ByteArray(0)
    //     val imageData = mapOf("key" to "value")
    //     return imageRepository.uploadImage(image, imageData)
    // }

    fun logoutUser() {
        _sessionToken = ""
        _userName = null
        _userEmail = null
        _lastLoginResult.postValue(SimpleResult(isSuccess = false, errorMessage = "User logged out"))
        Log.d("LoginManager", "User logged out")
    }

    private fun createApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val developmentBaseUrl = "https://dev.mdeyecare.com/api/v1/"

        return Retrofit.Builder()
            .baseUrl(developmentBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}