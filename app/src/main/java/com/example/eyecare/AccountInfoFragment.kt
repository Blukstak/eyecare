package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.eyecare.data.factory.SettingItemFactory
import com.example.eyecare.ui.login.LoginManager
import SettingItem

class AccountInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_info, container, false)

        // Set user details
        val userNameTextView = view.findViewById<TextView>(R.id.userName)
        val userEmailTextView = view.findViewById<TextView>(R.id.userEmail)
        userNameTextView.text = LoginManager.getUserName()
        userEmailTextView.text = LoginManager.getUserEmail()

        // Set account info items
        val accountInfoList = view.findViewById<LinearLayout>(R.id.accountInfoList)
        val items = listOf(
            SettingItemFactory.createValueItem(android.R.drawable.ic_menu_info_details, "Wallet Balance", "29 photos"),
            SettingItemFactory.createValueItem(android.R.drawable.ic_menu_manage, "Master Account", "true"),
            SettingItemFactory.createValueItem(android.R.drawable.ic_menu_preferences, "Super Admin", "true")
        )

        for (item in items) {
            val itemView = layoutInflater.inflate(R.layout.item_setting_value, accountInfoList, false)
            bindItemView(itemView, item as SettingItem.ValueItem)
            accountInfoList.addView(itemView)
        }

        // Set logout button action
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Log out the user
            LoginManager.logoutUser()
            // Navigate to the StepsFragment
            (activity as? MenuActivity)?.switchFragment(StepsFragment(), addToBackStack = false)
        }

        return view
    }

    private fun bindItemView(itemView: View, item: SettingItem.ValueItem) {
        val icon = itemView.findViewById<ImageView>(R.id.itemIcon)
        val text = itemView.findViewById<TextView>(R.id.itemText)
        val value = itemView.findViewById<TextView>(R.id.itemValue)

        icon.setImageResource(item.iconRes ?: android.R.drawable.ic_menu_help)
        text.text = item.text
        value.text = item.value
    }
}