package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class AboutEyecareFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_eyecare, container, false)

        val termsOfService = view.findViewById<TextView>(R.id.termsOfService)
        termsOfService.setOnClickListener {
            (activity as? MenuActivity)?.switchFragment(TermsOfServiceFragment())
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as? MenuActivity)?.updateHeader(AboutEyecareHeaderFragment())
        (activity as? MenuActivity)?.updateHeaderText("About Eyecare")
    }
}