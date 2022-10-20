package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentAnimationBinding
import com.vados.nasa_photo.databinding.FragmentAnimationStartBinding
import com.vados.nasa_photo.databinding.FragmentGreetingsFirstBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimationFragment:Fragment() {

    private var _binding: FragmentAnimationStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}