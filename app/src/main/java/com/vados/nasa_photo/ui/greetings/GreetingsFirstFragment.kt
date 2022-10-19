package com.vados.nasa_photo.ui.greetings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentGreetingsFirstBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GreetingsFirstFragment:Fragment() {

    private var _binding: FragmentGreetingsFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsFirstBinding.inflate(inflater)
        binding.imageViewSwipe.animate().rotation(-15.0f).startDelay = 700
        GlobalScope.launch {
            delay(1500)
            binding.imageViewSwipe.visibility = View.INVISIBLE
        }

        return binding.root
    }

}