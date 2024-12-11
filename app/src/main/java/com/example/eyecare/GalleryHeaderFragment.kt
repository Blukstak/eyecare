
package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class GalleryHeaderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.header_gallery, container, false)

        val backArrow = view.findViewById<ImageView>(R.id.backArrow)
        val headerTitle = view.findViewById<TextView>(R.id.headerTitle)
        val shareIcon = view.findViewById<ImageView>(R.id.shareIcon)
        val downloadIcon = view.findViewById<ImageView>(R.id.downloadIcon)

        headerTitle.text = "Gallery"

        backArrow.setOnClickListener {
            activity?.onBackPressed()
        }

        // Implement share and download functionality as needed
        shareIcon.setOnClickListener {
            // Share functionality
        }

        downloadIcon.setOnClickListener {
            // Download functionality
        }

        return view
    }
}