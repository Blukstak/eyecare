package com.example.settingsapp

import SettingItem
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eyecare.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsList = findViewById<LinearLayout>(R.id.settingsList)

        val items = listOf(
            SettingItem(R.drawable.main, "Subjects"),
            SettingItem(R.drawable.main, "Enable Shutter Sound", hasToggle = true),
            SettingItem(R.drawable.main, "Enable Guide", hasToggle = true, isToggleOn = true),
            SettingItem(R.drawable.main, "Enable Auto Fill", hasToggle = true),
            SettingItem(R.drawable.main, "About Eyecare"),
            SettingItem(R.drawable.main, "BLE Eyecare"),
            SettingItem(R.drawable.autofill80, "Advanced Camera", hasToggle = true)
        )

        for (item in items) {
            val itemView = layoutInflater.inflate(R.layout.item_setting, settingsList, false)

            val icon = itemView.findViewById<ImageView>(R.id.itemIcon)
            val text = itemView.findViewById<TextView>(R.id.itemText)
            val toggle = itemView.findViewById<Switch>(R.id.itemSwitch)

            icon.setImageResource(item.iconRes)
            text.text = item.text

            if (item.hasToggle) {
                toggle.visibility = View.VISIBLE
                toggle.isChecked = item.isToggleOn
            }

            settingsList.addView(itemView)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}
