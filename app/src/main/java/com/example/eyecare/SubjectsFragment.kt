package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eyecare.data.factory.SettingItemFactory
import BaseSettingItem
import SettingItem

class SubjectsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_subjects, container, false)
        val settingsList = view.findViewById<LinearLayout>(R.id.settingsList)

        val items = listOf(
            SettingItemFactory.createToggleItem(R.drawable.main, "Enable Shutter Sound"),
            SettingItemFactory.createToggleItem(R.drawable.main, "Enable Guide", isToggleOn = true),
            SettingItemFactory.createToggleItem(R.drawable.main, "Enable Auto Fill")
        )

        for (item in items) {
            val itemView = when (item) {
                is SettingItem.ToggleItem -> inflater.inflate(R.layout.item_setting_toggle, settingsList, false)
                is SettingItem.ButtonItem -> inflater.inflate(R.layout.item_setting_button, settingsList, false)
                is SettingItem.ValueItem -> inflater.inflate(R.layout.item_setting_value, settingsList, false)
                else -> createWarningView("Unsupported setting item type: ${item::class.java.simpleName}")
            }

            if (itemView != null) {
                bindItemView(itemView, item)
                settingsList.addView(itemView)
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as? MenuActivity)?.updateHeader(SubjectsHeaderFragment())
        (activity as? MenuActivity)?.updateHeaderText("Subjects")
    }

    private fun bindItemView(itemView: View, item: BaseSettingItem) {
        val icon = itemView.findViewById<ImageView>(R.id.itemIcon)
        val text = itemView.findViewById<TextView>(R.id.itemText)

        icon?.setImageResource(item.iconRes ?: R.drawable.main)
        text?.text = item.text

        when (item) {
            is SettingItem.ToggleItem -> {
                val toggle = itemView.findViewById<Switch>(R.id.itemSwitch)
                toggle?.isChecked = item.isToggleOn
            }
            is SettingItem.ButtonItem -> {
                val buttonLayout = itemView.findViewById<LinearLayout>(R.id.itemButton)
                buttonLayout?.setOnClickListener {
                    Toast.makeText(requireContext(), "${item.text} clicked", Toast.LENGTH_SHORT).show()
                }
            }
            is SettingItem.ValueItem -> {
                val value = itemView.findViewById<TextView>(R.id.itemValue)
                value?.text = item.value
            }
        }
    }

    private fun createWarningView(message: String): View {
        val warningView = TextView(requireContext())
        warningView.text = message
        warningView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        return warningView
    }
}