package com.example.eyecare

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.eyecare.R
import com.example.eyecare.ui.gallery.GalleryFragment

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MenuActivity", "MenuActivity Created")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Retrieve fragment types from intent extras
        val headerFragmentType = intent.getStringExtra("HEADER_FRAGMENT")?.let { FragmentType.valueOf(it) }
        val contentFragmentType = intent.getStringExtra("CONTENT_FRAGMENT")?.let { FragmentType.valueOf(it) }

        // Load the specified header fragment if provided
        if (headerFragmentType != null && savedInstanceState == null) {
            val headerFragment = createFragment(headerFragmentType)
            if (headerFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.headerContainer, headerFragment)
                    .commit()
            }
        }

        // Load the specified content fragment if provided
        if (contentFragmentType != null && savedInstanceState == null) {
            val contentFragment = createFragment(contentFragmentType)
            if (contentFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.dynamicContentContainer, contentFragment)
                    .commit()
            }
        }
    }

    private fun createFragment(fragmentType: FragmentType): Fragment? {
        return when (fragmentType) {
            FragmentType.GALLERY_HEADER -> GalleryHeaderFragment()
            FragmentType.DEFAULT_HEADER -> DefaultHeaderFragment()
            FragmentType.GALLERY -> GalleryFragment()
            FragmentType.SETTINGS -> SettingsFragment()
            FragmentType.LOGIN -> LoginFragment() // Add this line to handle LoginFragment
        }
    }

    fun switchFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right)
            .replace(R.id.dynamicContentContainer, fragment)
            //.addToBackStack(null) // Add this line to ensure the fragment is added to the back stack
        
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        
        transaction.commit()
    }

    fun updateHeader(newHeaderFragment: Fragment) {
        Log.d("MenuActivity", "Updating header with: ${newHeaderFragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.headerContainer, newHeaderFragment)
            .commit()
    }

    fun updateHeaderText(title: String) {
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        headerTitle?.text = title
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            updateHeader(DefaultHeaderFragment())
            updateHeaderText("Settings")
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}