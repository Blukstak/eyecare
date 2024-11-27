package com.example.eyecare

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ItemInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_information)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve the data passed via Intent
        val title = intent.getStringExtra("ITEM_TITLE")
        val imageResId = intent.getIntExtra("ITEM_IMAGE_RES_ID", -1)

        // Set the data to the views
        val itemTitle = findViewById<TextView>(R.id.item_title)
        val itemImage = findViewById<ImageView>(R.id.item_image)

        itemTitle.text = title
        if (imageResId != -1) {
         //   itemImage.setImageResource(imageResId)
        }
    }
}