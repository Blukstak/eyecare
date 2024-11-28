package com.example.eyecare

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity(), ImageGalleryAdapter.OnItemClickListener {

    private lateinit var binding: ActivityGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Initialize and setup the RecyclerView
        val navRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        setupImageGalleryRecyclerView(this, navRecyclerView, this)
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, ItemInformation::class.java)
        intent.putExtra("ITEM_TITLE", position)
        intent.putExtra("ITEM_IMAGE_RES_ID", position)
        startActivity(intent)
    }
}