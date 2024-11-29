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

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val userName = "Pablo Furia" // This should be dynamically set based on the current user
        val userInfoBlock = findViewById<LinearLayout>(R.id.userInfoBlock)
        val userNameTextView = findViewById<TextView>(R.id.userName)
        userNameTextView.text = userName

        userInfoBlock.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right)
                .replace(android.R.id.content, AccountInfoFragment())
                .addToBackStack(null)
                .commit()
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
                        when (item.text) {
                            "Subjects" -> {
                                supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right)
                                    .replace(android.R.id.content, SubjectsFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                            "About Eyecare" -> {
                                supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right)
                                    .replace(android.R.id.content, AboutEyecareFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                            else -> {
                                Log.d("SettingsActivity", "Button '${item.text}' clicked")
                            }
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
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}
