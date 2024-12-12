package com.example.myapp.debug

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.content.FileProvider
import com.example.eyecare.R
import com.example.eyecare.ui.login.LoginManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class DebugMenu(context: Context, private val parentLayout: FrameLayout, private val loginManager: LoginManager) {

    private val debugMenuView: View =
        LayoutInflater.from(context).inflate(R.layout.debug_menu, parentLayout, false)

    init {
        // Add the debug menu to the parent layout
        parentLayout.addView(debugMenuView)

        // Debug menu components
        val debugCommandInput = debugMenuView.findViewById<EditText>(R.id.debugCommandInput)
        val debugExecuteButton = debugMenuView.findViewById<Button>(R.id.debugExecuteButton)
        val logHelloButton = debugMenuView.findViewById<Button>(R.id.logHelloButton)
        val showTimeButton = debugMenuView.findViewById<Button>(R.id.showTimeButton)
        val clearLogsButton = debugMenuView.findViewById<Button>(R.id.clearLogsButton)
        val listVideosButton = debugMenuView.findViewById<Button>(R.id.listVideosButton)
        val openLastVideoButton = debugMenuView.findViewById<Button>(R.id.openLastVideoButton)
        val loginButton = debugMenuView.findViewById<Button>(R.id.loginButton)
        val getUserInfoButton = debugMenuView.findViewById<Button>(R.id.getUserInfoButton)
        val getUserPatientsButton = debugMenuView.findViewById<Button>(R.id.getUserPatientsButton)
        val registerPatientButton = debugMenuView.findViewById<Button>(R.id.registerPatientButton)
        val getUserCloudImageInfoButton = debugMenuView.findViewById<Button>(R.id.getUserCloudImageInfoButton)
        val getImageButton = debugMenuView.findViewById<Button>(R.id.getImageButton)

        // Set up button listeners
        debugExecuteButton.setOnClickListener {
            val command = debugCommandInput.text.toString()
            executeDebugCommand(command)
        }

        logHelloButton.setOnClickListener { executeDebugCommand("LOG_HELLO") }
        showTimeButton.setOnClickListener { executeDebugCommand("SHOW_TIME") }
        clearLogsButton.setOnClickListener { clearLogs() }
        listVideosButton.setOnClickListener { executeDebugCommand("LIST_VIDEOS") }
        openLastVideoButton.setOnClickListener { executeDebugCommand("OPEN_LAST_VIDEO") }
        loginButton.setOnClickListener { loginUser() }
        getUserInfoButton.setOnClickListener { getUserInfo() }
        getUserPatientsButton.setOnClickListener { getUserPatients() }
        registerPatientButton.setOnClickListener { registerPatient() }
        getUserCloudImageInfoButton.setOnClickListener { getUserCloudImageInfo() }
        getImageButton.setOnClickListener { getImage() }

        // Hide menu initially
        debugMenuView.visibility = View.GONE
    }

    fun toggleVisibility() {
        debugMenuView.visibility =
            if (debugMenuView.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    private fun executeDebugCommand(command: String) {
        when (command) {
            "LOG_HELLO" -> Log.d("DEBUG_MENU", "Hello from debug menu!")
            "SHOW_TIME" -> Log.d("DEBUG_MENU", "Current time: ${System.currentTimeMillis()}")
            "LIST_VIDEOS" -> listVideos()
            "OPEN_LAST_VIDEO" -> openLastVideo()
            else -> Log.d("DEBUG_MENU", "Unknown command: $command")
        }
    }

    private fun listVideos() {
        val videoDir = parentLayout.context.externalMediaDirs.firstOrNull() ?: return
        val videoFiles = videoDir.listFiles { file -> file.extension == "mp4" } ?: return
        if (videoFiles.isEmpty()) {
            Log.d("DEBUG_MENU", "No video files found")
        } else {
            videoFiles.forEach { file ->
                Log.d("DEBUG_MENU", "Video file: ${file.absolutePath}")
            }
        }
    }

    private fun openLastVideo() {
        val videoDir = parentLayout.context.externalMediaDirs.firstOrNull() ?: return
        val videoFiles = videoDir.listFiles { file -> file.extension == "mp4" }?.sortedByDescending { it.lastModified() } ?: return
        if (videoFiles.isNotEmpty()) {
            val lastVideo = videoFiles.first()
            val videoUri: Uri = FileProvider.getUriForFile(
                parentLayout.context,
                "${parentLayout.context.packageName}.fileprovider",
                lastVideo
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(videoUri, "video/mp4")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            parentLayout.context.startActivity(intent)
        } else {
            Log.d("DEBUG_MENU", "No video files found")
        }
    }

    private fun clearLogs() {
        Log.d("DEBUG_MENU", "Logs cleared (simulation)")
    }

    private fun loginUser() {
        val userEmail = "zoserx@gmail.com"
        val userAlias = "zoserx@gmail.com"
        val userPassword = "kaka22"
        loginManager.loginUser(userEmail, userAlias, userPassword)
    }

    private fun getUserInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = loginManager.getUserInfo()
            Log.d("DEBUG_MENU", "Get User Info result: ${result.isSuccess}, ${result.errorMessage}")
        }
    }

    private fun getUserPatients() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = loginManager.getUserPatients()
            Log.d("DEBUG_MENU", "Get User Patients result: ${result.isSuccess}, ${result.errorMessage}")
        }
    }

    private fun registerPatient() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = loginManager.registerPatient()
            Log.d("DEBUG_MENU", "Register Patient result: ${result.isSuccess}, ${result.errorMessage}")
        }
    }

    private fun getUserCloudImageInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = loginManager.getUserCloudImageInfo()
            Log.d("DEBUG_MENU", "Get User Cloud Image Info result: ${result.isSuccess}, ${result.errorMessage}")
        }
    }

    private fun getImage() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = loginManager.getImage()
            Log.d("DEBUG_MENU", "Get Image result: ${result.isSuccess}, ${result.errorMessage}")
        }
    }
}
