package com.example.settingsapp

import BaseSettingItem
import SettingItem
import com.example.eyecare.data.factory.SettingItemFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eyecare.R
import com.example.eyecare.SubjectsFragment
import com.example.eyecare.AboutEyecareFragment
import com.example.eyecare.TermsOfServiceFragment
import android.content.Intent
import com.example.eyecare.AccountInfoFragment
import androidx.fragment.app.Fragment
import com.example.eyecare.DefaultHeaderFragment
import android.widget.FrameLayout
import android.view.Gravity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Load the default header
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.headerContainer, DefaultHeaderFragment())
                .commit()
        }

        val userName = "Pablo Furia" // This should be dynamically set based on the current user
        val userInfoBlock = findViewById<LinearLayout>(R.id.userInfoBlock)
        val userNameTextView = findViewById<TextView>(R.id.userName)
        userNameTextView.text = userName

        userInfoBlock.setOnClickListener {
            replaceFragment(AccountInfoFragment())
        }

        val settingsList = findViewById<LinearLayout>(R.id.settingsList)

        val items = listOf(
            SettingItemFactory.createButtonItem(R.drawable.main, "Subjects"),
            SettingItemFactory.createToggleItem(R.drawable.main, "Enable Shutter Sound"),
            SettingItemFactory.createToggleItem(R.drawable.main, "Enable Guide", isToggleOn = true),
            SettingItemFactory.createToggleItem(R.drawable.main, "Enable Auto Fill"),
            SettingItemFactory.createButtonItem(R.drawable.main, "About Eyecare"),
            SettingItemFactory.createButtonItem(R.drawable.main, "BLE Eyecare"),
            SettingItemFactory.createToggleItem(R.drawable.autofill80, "Advanced Camera"),
            SettingItemFactory.createValueItem(R.drawable.main, "Current Version", "1.0.0")
        )

        for (item in items) {
            val itemView = when (item) {
                is SettingItem.ToggleItem -> layoutInflater.inflate(R.layout.item_setting_toggle, settingsList, false)
                is SettingItem.ButtonItem -> layoutInflater.inflate(R.layout.item_setting_button, settingsList, false)
                is SettingItem.ValueItem -> layoutInflater.inflate(R.layout.item_setting_value, settingsList, false)
                else -> createWarningView("Unsupported setting item type: ${item::class.java.simpleName}")
            }

            if (itemView != null) {
                bindItemView(itemView, item)
                settingsList.addView(itemView)
            } else {
                Log.e("SettingsActivity", "Failed to inflate view for item: ${item.text}")
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        Log.d("SettingsActivity", "Replacing fragment with: ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right)
            .replace(R.id.contentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun updateHeader(newHeaderFragment: Fragment) {
        Log.d("SettingsActivity", "Updating header with: ${newHeaderFragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.headerContainer, newHeaderFragment)
            .commit()
    }

    fun updateHeaderText(title: String) {
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        headerTitle?.text = title
    }

    private fun bindItemView(itemView: View, item: BaseSettingItem) {
        val icon = itemView.findViewById<ImageView>(R.id.itemIcon)
        val text = itemView.findViewById<TextView>(R.id.itemText)

        if (icon == null) {
            Log.e("SettingsActivity", "ImageView with ID 'itemIcon' not found in layout for item: ${item.text}")
            return
        }

        if (text == null) {
            Log.e("SettingsActivity", "TextView with ID 'itemText' not found in layout for item: ${item.text}")
            return
        }

        val iconRes = item.iconRes ?: run {
            Log.w("SettingsActivity", "Icon resource is null for item: ${item.text}")
            R.drawable.main // Use a placeholder warning icon
        }

        icon.setImageResource(iconRes)
        text.text = item.text

        when (item) {
            is SettingItem.ToggleItem -> {
                val toggle = itemView.findViewById<Switch>(R.id.itemSwitch)
                if (toggle != null) {
                    toggle.isChecked = item.isToggleOn
                } else {
                    Log.e("SettingsActivity", "Switch with ID 'itemSwitch' not found in layout for item: ${item.text}")
                }
            }
            is SettingItem.ButtonItem -> {
                val buttonLayout = itemView.findViewById<LinearLayout>(R.id.itemButton)
                if (buttonLayout != null) {
                    buttonLayout.setOnClickListener {
                        Log.d("SettingsActivity", "Button '${item.text}' clicked")
                        when (item.text) {
                            "Subjects" -> {
                                replaceFragment(SubjectsFragment())
                                updateHeaderText("Subjects")
                            }
                            "About Eyecare" -> {
                                replaceFragment(AboutEyecareFragment())
                                updateHeaderText("About Eyecare")
                            }
                            else -> Log.d("SettingsActivity", "Unhandled button '${item.text}' clicked")
                        }
                    }
                } else {
                    Log.e("SettingsActivity", "LinearLayout with ID 'itemButton' not found in layout for item: ${item.text}")
                }
            }
            is SettingItem.ValueItem -> {
                val value = itemView.findViewById<TextView>(R.id.itemValue)
                if (value != null) {
                    value.text = item.value
                } else {
                    Log.e("SettingsActivity", "TextView with ID 'itemValue' not found in layout for item: ${item.text}")
                }
            }
            else -> {}
        }
    }

    private fun createWarningView(message: String): View {
        val warningView = TextView(this)
        warningView.text = message
        warningView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        return warningView
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
