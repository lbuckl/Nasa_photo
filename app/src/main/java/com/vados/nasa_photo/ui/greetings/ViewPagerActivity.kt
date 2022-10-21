package com.vados.nasa_photo.ui.greetings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vados.nasa_photo.databinding.ActivityViewPagerBinding

class ViewPagerActivity: AppCompatActivity() {
    private var _binding:ActivityViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}