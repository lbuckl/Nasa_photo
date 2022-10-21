package com.vados.nasa_photo.ui.greetings

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
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

        //Распределение времени для анимации и скрытия
        val duration = 1000L
        val startDelay = 1000L
        val visionDelay = duration + startDelay + 1500

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = RenderEffect.createBlurEffect(10f, 0f,
                Shader.TileMode.MIRROR)
            with(binding){
                imageViewEarth.setRenderEffect(blurEffect)
                textViewGeneral.setRenderEffect(blurEffect)
                textViewSupport.setRenderEffect(blurEffect)
            }
            //binding.smoothGroop.setRenderEffect(blurEffect)
        }

        ViewCompat.animate(binding.imageViewSwipe)
            .translationX(-100.0f)
            .setDuration(duration)
            .setInterpolator(CycleInterpolator(2F))
            .setStartDelay(startDelay).start()

        GlobalScope.launch {
            delay(visionDelay)
            binding.imageViewSwipe.visibility = View.INVISIBLE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blurEffect = RenderEffect.createBlurEffect(0.1f, 0f,
                    Shader.TileMode.MIRROR)
                with(binding){
                    imageViewEarth.setRenderEffect(blurEffect)
                    textViewGeneral.setRenderEffect(blurEffect)
                    textViewSupport.setRenderEffect(blurEffect)
                }

                //binding.smoothGroop.setRenderEffect(blurEffect)
            }
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}