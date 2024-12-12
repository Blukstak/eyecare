package com.example.eyecare.data.repository

import android.util.Log
import com.example.eyecare.data.model.SimpleResult
import com.example.eyecare.data.network.ApiService
import com.google.gson.Gson

class UserRepository(private val apiService: ApiService) {
    suspend fun getUserInfo(username: String, alias: String, token: String): SimpleResult {
        val params = mapOf(
            "username" to username,
            "alias" to alias,
            "token" to token
        )

        return try {
            val response = apiService.getUserInfo("account/mobile/getUserInfo", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("UserRepository", "User info retrieved successfully")
                    SimpleResult(isSuccess = true, errorMessage = "", data = data)
                } else {
                    Log.e("UserRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("UserRepository", "Get user info failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Get user info failed with exception: ${e.message}")
            SimpleResult(isSuccess = false, errorMessage = e.message ?: "Unknown error")
        }
    }

    suspend fun getUserPatients(username: String, alias: String, token: String, page: Int, perPage: Int): SimpleResult {
        val params = mutableMapOf(
            "username" to username,
            "alias" to alias,
            "token" to token
        )
        if (page > 0) {
            val skip = (page - 1) * perPage
            params["skip"] = skip.toString()
            params["limit"] = perPage.toString()
        }

        return try {
            val response = apiService.getUserPatients("account/mobile/getEyeImageUsers", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("UserRepository", "User patients retrieved successfully")
                    SimpleResult(isSuccess = true, errorMessage = "", data = data)
                } else {
                    Log.e("UserRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("UserRepository", "Get user patients failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Get user patients failed with exception: ${e.message}")
            SimpleResult(isSuccess = false, errorMessage = e.message ?: "Unknown error")
        }
    }

    suspend fun registerPatient(identifier: String, birthDate: Long, email: String, consentImage: ByteArray): SimpleResult {
        val params = mapOf(
            "identifier" to identifier,
            "emailAddress" to email,
            "dob" to birthDate.toString()
        )

        return try {
            val response = apiService.registerPatient("account/mobile/createEyeImageUser", params, consentImage)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("UserRepository", "Patient registered successfully")
                    SimpleResult(isSuccess = true, errorMessage = "")
                } else {
                    Log.e("UserRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("UserRepository", "Register patient failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Register patient failed with exception: ${e.message}")
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