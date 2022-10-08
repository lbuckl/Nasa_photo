package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vados.nasa_photo.databinding.ActivityViewPagerBinding

class ViewPagerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}