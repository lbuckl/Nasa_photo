package com.vados.nasa_photo.ui.greetings

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentGreetingsFirstBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.io.path.Path

class GreetingsFirstFragment:Fragment() {

    private var _binding: FragmentGreetingsFirstBinding? = null
    private val binding get() = _binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsFirstBinding.inflate(inflater)

        ViewCompat.animate(binding.imageViewSwipe)
            .translationX(-100.0f)
            .setDuration(1000)
            .setInterpolator(CycleInterpolator(2F))
            .setStartDelay(1000).start()

        GlobalScope.launch {
            delay(5000)
            binding.imageViewSwipe.visibility = View.INVISIBLE
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}