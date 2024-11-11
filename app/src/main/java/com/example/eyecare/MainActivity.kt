package com.example.eyecare

import CameraFragment
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val navRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        setupImageGalleryRecyclerView(this, navRecyclerView)

        drawerLayout = findViewById(R.id.drawer_layout)

        // Populate the RecyclerView with a sample list of image resources
        val imageList = listOf(R.drawable.testrectangle, R.drawable.testrectangle, R.drawable.testrectangle     ) // Add your image resources

/*        val navRecyclerView = findViewById<RecyclerView>(R.id.nav_recycler_view)
        val drawerItems = listOf(
            ImageGalleryAdapter.DrawerItem("Home", R.drawable.testrectangle),
            ImageGalleryAdapter.DrawerItem("Gallery", R.drawable.testrectangle),
            ImageGalleryAdapter.DrawerItem("Slideshow", R.drawable.testrectangle)
        )
        navRecyclerView.layoutManager = LinearLayoutManager(this)
        navRecyclerView.adapter = ImageGalleryAdapter(drawerItems)*/

        // Button to open the drawer
        findViewById<Button>(R.id.button1).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Load the CameraFragment into the container
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.camera_fragment_container, CameraFragment())
                .commit()
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}