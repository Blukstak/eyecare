package com.example.eyecare

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.eyecare.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Load the default header
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.headerContainer, DefaultHeaderFragment())
                .commit()
        }

        // Load the settings fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.dynamicContentContainer, SettingsFragment())
                .commit()
        }
    }

    fun switchFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right)
            .replace(R.id.dynamicContentContainer, fragment)
        
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