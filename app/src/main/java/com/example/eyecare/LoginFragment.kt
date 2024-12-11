package com.example.eyecare

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eyecare.ui.login.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val username = view.findViewById<EditText>(R.id.username)
        val alias = view.findViewById<EditText>(R.id.alias)
        val password = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val forgotPassword = view.findViewById<TextView>(R.id.forgotPassword)
        val wantAccount = view.findViewById<TextView>(R.id.wantAccount)

        loginButton.setOnClickListener {
            val user = username.text.toString()
            val userAlias = alias.text.toString()
            val pass = password.text.toString()
            if (user.isNotEmpty() && pass.isNotEmpty()) {
                loginViewModel.login(user, userAlias, pass)
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

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            if (result.isSuccess) {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                // Navigate back to MainActivity
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Login failed: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
}