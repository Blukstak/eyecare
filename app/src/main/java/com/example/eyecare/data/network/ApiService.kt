package com.example.eyecare.data.network

import com.example.eyecare.data.model.LoginResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface ApiService {
    @PUT
    suspend fun login(
        @Url route: String,
        @Body params: Map<String, String>
    ): Response<LoginResponseBody>
}