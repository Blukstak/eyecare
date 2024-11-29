package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.eyecare.R
import com.example.eyecare.TermsOfServiceFragment

class AboutEyecareFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_eyecare, container, false)

        val termsOfService = view.findViewById<TextView>(R.id.termsOfService)
        termsOfService.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right)
                .replace(android.R.id.content, TermsOfServiceFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}