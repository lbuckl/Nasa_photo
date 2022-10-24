package com.vados.nasa_photo.ui.greetings

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentGreetingsFirstBinding
import kotlinx.coroutines.*

/**
 * Первый из трёх приветственных фрагментов
 * всплывает только при первом запуске
 */
class GreetingsFirstFragment : Fragment() {

    private var _binding: FragmentGreetingsFirstBinding? = null
    private val binding get() = _binding!!
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsFirstBinding.inflate(inflater)

        //Распределение времени для анимации и скрытия
        val duration = 1000L
        val startDelay = 1000L
        val visionDelay = duration + startDelay + 1000

        //включаем рендер эффект
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = RenderEffect.createBlurEffect(
                10f, 0f,
                Shader.TileMode.MIRROR
            )
            with(binding) {
                imageViewEarth.setRenderEffect(blurEffect)
                textViewGeneral.setRenderEffect(blurEffect)
                textViewSupport.setRenderEffect(blurEffect)
            }
        }

        //Анимация свайпа
        ViewCompat.animate(binding.imageViewSwipe)
            .translationX(-100.0f)
            .setDuration(duration)
            .setInterpolator(CycleInterpolator(2F))
            .setStartDelay(startDelay).start()

        //Убираем подсказку и обнуляем рендер
        coroutineScope.launch {
            delay(visionDelay)
            binding.imageViewSwipe.visibility = View.INVISIBLE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blurEffect = RenderEffect.createBlurEffect(
                    0.1f, 0f,
                    Shader.TileMode.MIRROR
                )
                with(binding) {
                    imageViewEarth.setRenderEffect(blurEffect)
                    textViewGeneral.setRenderEffect(blurEffect)
                    textViewSupport.setRenderEffect(blurEffect)
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        _binding = null
    }

}