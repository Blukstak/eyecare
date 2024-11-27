package com.example.eyecare.data.model

interface ResultInterpreter {
    val isSuccess: Boolean
    val errorMessage: String?
    val data: Any?
}

data class SimpleResult(
    override val isSuccess: Boolean,
    val errorCode: Int? = null,
    override val errorMessage: String,
    override val data: Any? = null
) : ResultInterpreter