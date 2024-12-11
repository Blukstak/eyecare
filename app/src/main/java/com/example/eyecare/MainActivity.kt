package com.example.eyecare

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.data.network.ApiService
import com.example.eyecare.databinding.ActivityMainBinding
import com.example.eyecare.ui.gallery.GalleryFragment
import com.example.eyecare.ui.login.LoginManager
import com.example.eyecare.ui.login.LoginViewModel
import com.example.eyecare.ui.login.LoginViewModelFactory
import com.example.myapp.debug.DebugMenu
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel

    // Variable to control the splash screen visibility condition
    private var keepSplashVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install the splash screen at the start of onCreate
        val splashScreen = installSplashScreen()

        // Keep the splash screen for an additional 1 second
        splashScreen.setKeepOnScreenCondition {
            // Condition for keeping the splash screen on
            keepSplashVisible
        }

        // Start a coroutine to remove the splash screen after 1 second
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)  // 1 second delay
            keepSplashVisible = false
        }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Get the LoginManager instance from the application class
        val loginManager = (application as MyApplication).loginManager

        // Get the ApiService instance from the application class
        val apiService = (application as MyApplication).apiService

        // Initialize LoginViewModel using LoginViewModelFactory
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(loginManager)).get(LoginViewModel::class.java)

        // Set the user's name in the TextView
        val userNameTextView = findViewById<TextView>(R.id.user_name_text)
        loginManager.lastLoginResult.observe(this, Observer { result ->
            if (result.isSuccess) {
                userNameTextView.text = "${loginManager.getUserName()} (${loginManager.getUserEmail()})"
            } else {
                userNameTextView.text = "Login failed: ${result.errorMessage}"
            }
        })

        // Button to open the MenuActivity with GalleryFragment
        findViewById<Button>(R.id.button1).setOnClickListener {
            openMenuWithGalleryFragment()
        }

        // Load the CameraFragment into the container
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.camera_fragment_container, CameraFragment())
                .commit()
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // Initialize debug menu
        val parentLayout = findViewById<FrameLayout>(R.id.camera_fragment_container)
        val debugFloatingButton = findViewById<Button>(R.id.debugFloatingButton)
        val debugMenu = DebugMenu(this, parentLayout, loginManager)

        // Toggle debug menu visibility
        debugFloatingButton.setOnClickListener {
            debugMenu.toggleVisibility()
        }

        // Modify the right button to open MenuActivity with SettingsFragment
        findViewById<Button>(R.id.button2).setOnClickListener {
            openMenuWithSettingsFragment()
        }

        // Check if the user is authenticated
        if (!loginManager.IsLoggedIn()) {
            // Launch the MenuActivity and set it to the LoginFragment
            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("HEADER_FRAGMENT", FragmentType.DEFAULT_HEADER.name)
                putExtra("CONTENT_FRAGMENT", FragmentType.LOGIN.name)
            }
            startActivity(intent)
            finish()
            return
        }
        
    }

    private fun openMenuWithGalleryFragment() {
        val intent = Intent(this, MenuActivity::class.java).apply {
            putExtra("HEADER_FRAGMENT", FragmentType.GALLERY_HEADER.name)
            putExtra("CONTENT_FRAGMENT", FragmentType.GALLERY.name)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    private fun openMenuWithSettingsFragment() {
        val intent = Intent(this, MenuActivity::class.java).apply {
            putExtra("HEADER_FRAGMENT", FragmentType.DEFAULT_HEADER.name)
            putExtra("CONTENT_FRAGMENT", FragmentType.SETTINGS.name)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
