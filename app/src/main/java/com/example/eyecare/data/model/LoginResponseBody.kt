
package com.example.eyecare.data.model

data class LoginResponseBody(
    val success: Boolean,
    val error: String,
    val data: LoginResponseBodyData
)

data class LoginResponseBodyData(
    val token: String
)
