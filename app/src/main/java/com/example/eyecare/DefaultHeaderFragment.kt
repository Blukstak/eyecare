package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.ImageView

class DefaultHeaderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_default_header, container, false)

        val backButton: ImageView = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }
}