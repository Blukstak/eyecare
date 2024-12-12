package com.example.eyecare.data.repository

import android.util.Log
import com.example.eyecare.data.model.SimpleResult
import com.example.eyecare.data.network.ApiService
import com.google.gson.Gson

class ImageRepository(private val apiService: ApiService) {
    suspend fun getUserCloudImageInfo(username: String, alias: String, token: String, page: Int, perPage: Int): SimpleResult {
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
            val response = apiService.getUserCloudImageInfo("eyeImages/mobile", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("ImageRepository", "User cloud images retrieved successfully")
                    SimpleResult(isSuccess = true, errorMessage = "", data = data)
                } else {
                    Log.e("ImageRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("ImageRepository", "Get user cloud images failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("ImageRepository", "Get user cloud images failed with exception: ${e.message}")
            SimpleResult(isSuccess = false, errorMessage = e.message ?: "Unknown error")
        }
    }

    suspend fun getImage(id: String, username: String, alias: String, token: String): SimpleResult {
        val params = mapOf(
            "imageId" to id,
            "username" to username,
            "alias" to alias,
            "token" to token
        )

        return try {
            val response = apiService.getImage("eyeImages/download/mobile", params)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("ImageRepository", "Image retrieved successfully")
                    SimpleResult(isSuccess = true, errorMessage = "", data = data)
                } else {
                    Log.e("ImageRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("ImageRepository", "Get image failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("ImageRepository", "Get image failed with exception: ${e.message}")
            SimpleResult(isSuccess = false, errorMessage = e.message ?: "Unknown error")
        }
    }

    suspend fun uploadImage(image: ByteArray, imageData: Map<String, String>): SimpleResult {
        return try {
            val response = apiService.uploadImage("eyeImages/mobile/upload", imageData, image)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("ImageRepository", "Image uploaded successfully")
                    SimpleResult(isSuccess = true, errorMessage = "", data = data)
                } else {
                    Log.e("ImageRepository", "Invalid response data")
                    SimpleResult(isSuccess = false, errorMessage = "Invalid response data")
                }
            } else {
                val errorResult = handleErrorResponse(response.code())
                Log.e("ImageRepository", "Upload image failed with code ${response.code()}: ${errorResult.errorMessage}")
                errorResult
            }
        } catch (e: Exception) {
            Log.e("ImageRepository", "Upload image failed with exception: ${e.message}")
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