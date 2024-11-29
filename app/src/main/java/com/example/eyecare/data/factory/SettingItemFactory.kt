package com.example.eyecare.data.factory

import SettingItem

object SettingItemFactory {
    fun createBasicItem(iconRes: Int?, text: String): SettingItem {
        return SettingItem.BasicItem(iconRes, text)
    }

    fun createToggleItem(iconRes: Int?, text: String, isToggleOn: Boolean = false): SettingItem {
        return SettingItem.ToggleItem(iconRes, text, isToggleOn)
    }

    fun createButtonItem(iconRes: Int?, text: String): SettingItem {
        return SettingItem.ButtonItem(iconRes, text)
    }

    fun createValueItem(iconRes: Int?, text: String, value: String): SettingItem {
        return SettingItem.ValueItem(iconRes, text, value)
    }
}