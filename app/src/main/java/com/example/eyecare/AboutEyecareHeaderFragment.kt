package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class AboutEyecareHeaderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_about_eyecare_header, container, false)

        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }
}