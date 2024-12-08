
package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val username = view.findViewById<EditText>(R.id.username)
        val password = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val forgotPassword = view.findViewById<TextView>(R.id.forgotPassword)
        val wantAccount = view.findViewById<TextView>(R.id.wantAccount)

        loginButton.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()
            if (user.isNotEmpty() && pass.isNotEmpty()) {
                // Handle login logic here
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        forgotPassword.setOnClickListener {
            // Handle forgot password logic here
        }

        wantAccount.setOnClickListener {
            // Handle sign up logic here
        }

        return view
    }
}