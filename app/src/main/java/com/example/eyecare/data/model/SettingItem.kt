interface BaseSettingItem {
    val iconRes: Int?
    val text: String
}

sealed class SettingItem : BaseSettingItem {
    data class BasicItem(override val iconRes: Int?, override val text: String) : SettingItem()
    data class ToggleItem(override val iconRes: Int?, override val text: String, val isToggleOn: Boolean = false) : SettingItem()
    data class ButtonItem(override val iconRes: Int?, override val text: String) : SettingItem()
    data class ValueItem(override val iconRes: Int?, override val text: String, val value: String) : SettingItem()
}
