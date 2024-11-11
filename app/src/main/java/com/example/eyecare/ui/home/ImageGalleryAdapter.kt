package com.example.eyecare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R

// ImageGalleryAdapter class
class ImageGalleryAdapter(private val items: List<DrawerItem>) : RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder>() {

    // Data class for each drawer item
    data class DrawerItem(val title: String, val imageResId: Int)

    // ViewHolder class for holding views
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val textView: TextView = view.findViewById(R.id.text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photoviewer_imageitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textView.text = item.title
    }

    override fun getItemCount() = items.size
}

// Standalone function to provide a default list of drawer items
fun getDefaultDrawerItems(): List<ImageGalleryAdapter.DrawerItem> {
    return listOf(
        ImageGalleryAdapter.DrawerItem("Home", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Gallery", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Slideshow", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Profile", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Settings", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Notifications", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Messages", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Friends", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Favorites", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Photos", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Videos", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Downloads", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Music", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Documents", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Contacts", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Calendar", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Weather", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Reminders", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Tasks", R.drawable.testrectangle),
        ImageGalleryAdapter.DrawerItem("Help", R.drawable.testrectangle)
    )
}

// Standalone function to set up the RecyclerView with ImageGalleryAdapter
fun setupImageGalleryRecyclerView(context: Context, recyclerView: RecyclerView) {
    val drawerItems = getDefaultDrawerItems()
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = ImageGalleryAdapter(drawerItems)
}
