package com.example.eyecare.data.repository

import android.util.Log
import com.example.eyecare.data.model.SimpleResult
import com.example.eyecare.data.network.ApiService
import com.google.gson.Gson

// Note: AuthRepository is stateless and only acts as a bridge to communicate with the backend.
class AuthRepository(private val apiService: ApiService) {
    suspend fun loginUser(user: String, alias: String, pass: String): SimpleResult {
        val params = mapOf(
            "emailAddress" to user,
            "userAlias" to alias,
            "password" to pass
        )

        return try {
            val response = apiService.login("entrance/mobile/login", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("AuthRepository", "Login successful: ${data.data.token}")
                    Log.d("AuthRepository", "Response body: ${Gson().toJson(data)}")
                    SimpleResult(isSuccess = true, errorMessage = "", data = data)
                } else {
                    Log.e("AuthRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("AuthRepository", "Login failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed with exception: ${e.message}")
            SimpleResult(isSuccess = false, errorMessage = e.message ?: "Unknown error")
        }
    }

    private fun handleErrorResponse(code: Int): SimpleResult {
        return when (code) {
            401 -> SimpleResult(isSuccess = false, errorCode = 1, errorMessage = "Unauthorized")
            500 -> SimpleResult(isSuccess = false, errorCode = 2, errorMessage = "Server error")
            else -> SimpleResult(isSuccess = false, errorMessage = "Unknown error")
        }
    }
}