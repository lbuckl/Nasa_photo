package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.ActivityBottomNavigationViewBinding

class BottomNavigationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationViewBinding.inflate(layoutInflater)
        binding.bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    Toast.makeText(this, "Earth", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_view_mars -> {
                    Toast.makeText(this, "Mars", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_view_weather -> {
                    Toast.makeText(this, "Weather", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}