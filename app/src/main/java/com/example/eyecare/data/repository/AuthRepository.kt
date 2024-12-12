package com.example.eyecare.data.repository

import android.util.Log
import com.example.eyecare.data.model.SimpleResult
import com.example.eyecare.data.network.ApiService
import com.example.eyecare.data.network.LoginResponseBody
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

    suspend fun registerUser(name: String, email: String, pass: String): SimpleResult {
        val params = mapOf(
            "fullName" to name,
            "emailAddress" to email,
            "password" to pass
        )

        return try {
            val response = apiService.register("account/mobile/signup", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("AuthRepository", "Registration successful")
                    SimpleResult(isSuccess = true, errorMessage = "")
                } else {
                    Log.e("AuthRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("AuthRepository", "Registration failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Registration failed with exception: ${e.message}")
            SimpleResult(isSuccess = false, errorMessage = e.message ?: "Unknown error")
        }
    }

    suspend fun resetPassword(email: String): SimpleResult {
        val params = mapOf("emailAddress" to email)

        return try {
            val response = apiService.resetPassword("account/mobile/forgot", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("AuthRepository", "Password reset email sent")
                    SimpleResult(isSuccess = true, errorMessage = "")
                } else {
                    Log.e("AuthRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("AuthRepository", "Password reset failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Password reset failed with exception: ${e.message}")
            SimpleResult(isSuccess = false, errorMessage = e.message ?: "Unknown error")
        }
    }

    suspend fun sendFeedback(text: String): SimpleResult {
        val params = mapOf(
            "username" to "user", // Replace with actual user data
            "alias" to "alias",   // Replace with actual alias data
            "token" to "token",   // Replace with actual token data
            "feedback" to text
        )

        return try {
            val response = apiService.sendFeedback("account/mobile/uploadFeedback", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("AuthRepository", "Feedback sent successfully")
                    SimpleResult(isSuccess = true, errorMessage = "")
                } else {
                    Log.e("AuthRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("AuthRepository", "Feedback failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Feedback failed with exception: ${e.message}")
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