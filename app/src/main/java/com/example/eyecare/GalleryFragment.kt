package com.example.eyecare.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.GalleryHeaderFragment
import com.example.eyecare.MenuActivity
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentGalleryBinding
import android.widget.Toast
import com.example.eyecare.ImageDetailFragment

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ImageAdapter(getImages()) { imageRes ->
            (activity as? MenuActivity)?.switchFragment(ImageDetailFragment.newInstance(imageRes))
        }

        return root
    }

    private fun getImages(): List<Int> {
        // Replace with your logic to get the list of image resources
        return listOf(R.drawable.testrectangle, R.drawable.testrectangle, R.drawable.testrectangle)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MenuActivity)?.updateHeader(GalleryHeaderFragment())
        (activity as? MenuActivity)?.updateHeaderText("Gallery")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Adapter class for RecyclerView
class ImageAdapter(
    private val images: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_local_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageRes = images[position]
        holder.imageView.setImageResource(imageRes)
        holder.itemView.setOnClickListener {
            onItemClick(imageRes)
        }
    }

    override fun getItemCount() = images.size
}