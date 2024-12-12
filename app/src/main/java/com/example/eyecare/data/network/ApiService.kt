package com.example.eyecare.data.network

import com.example.eyecare.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @PUT
    suspend fun login(
        @Url route: String,
        @Body params: Map<String, String>
    ): Response<LoginResponseBody>

    @POST
    suspend fun register(
        @Url route: String,
        @Body params: Map<String, String>
    ): Response<Unit>

    @POST
    suspend fun resetPassword(
        @Url route: String,
        @Body params: Map<String, String>
    ): Response<Unit>

    @POST
    suspend fun sendFeedback(
        @Url route: String,
        @Body params: Map<String, String>
    ): Response<Unit>

    @GET
    suspend fun getUserInfo(
        @Url route: String,
        @QueryMap params: Map<String, String>
    ): Response<UserInfoResponseData>

    @GET
    suspend fun getUserPatients(
        @Url route: String,
        @QueryMap params: Map<String, String>
    ): Response<UserPatientsResponseData>

    @Multipart
    @POST
    suspend fun registerPatient(
        @Url route: String,
        @PartMap params: Map<String, String>,
        @Part consentImage: ByteArray
    ): Response<Unit>

    @GET
    suspend fun getUserCloudImageInfo(
        @Url route: String,
        @QueryMap params: Map<String, String>
    ): Response<UserImagesResponseData>

    @GET
    suspend fun getImage(
        @Url route: String,
        @QueryMap params: Map<String, String>
    ): Response<CloudImageData>

    @Multipart
    @POST
    suspend fun uploadImage(
        @Url route: String,
        @PartMap params: Map<String, String>,
        @Part image: ByteArray
    ): Response<UploadImageResponseData>
}

// Response Data Structures
data class LoginResponseBody(
    val success: Boolean,
    val error: String,
    val data: LoginResponseBodyData
)

data class LoginResponseBodyData(
    val token: String
)

data class SimpleResult(
    val success: Boolean,
    val error: String? = null
)

data class CloudImageData(
    val createdAt: Long,
    val updatedAt: Long,
    val id: String,
    val status: String,
    val identifier: String,
    val eyeImageUser: String?,
    val comments: String,
    val aliasUploader: String,
    val revisionSource: Int?,
    val revisionStatus: Int,
    val revisionText: String,
    val diagnosticStatus: Int,
    val diagnosticPrecision: Double,
    val diagnosticText: String,
    val manualDiagnosticDone: Boolean?,
    val manualDiagnosticStatus: Int?,
    val manualDiagnosticText: String?,
    val age: Int,
    val dob: String?,
    val dobFormatted: String,
    val takenAtFormatted: String?,
    val takenAt: String?
)

data class UserInfoResponseData(
    val userInfo: UserInfo
)

data class UserInfo(
    val userName: String,
    val userEmail: String,
    val isSuperAdmin: Boolean,
    val userAlias: String,
    val aliasPermissions: AliasPermissions,
    val wallet: Wallet
)

data class AliasPermissions(
    val CAN_VIEW_IMAGES: Boolean,
    val CAN_DELETE_IMAGES: Boolean
)

data class Wallet(
    val balance: Int
)

data class UserImagesResponseData(
    val userEyeImages: List<CloudImageData>
)

data class UploadImageResponseData(
    val error: String,
    val success: Boolean,
    val data: CloudImageData
)

data class UserPatientsResponseData(
    val eyeImageUsers: List<PatientData>
)

data class PatientData(
    val id: String,
    val identifier: String,
    val dobFormatted: String,
    val dob: Long,
    val emailAddress: String
)