package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.vados.nasa_photo.databinding.FragmentAnimationBinding
import kotlinx.coroutines.*

/**
 * Фрагмент для отображения окна анимации
 */
class AnimationFragment : Fragment() {
    private var _binding: FragmentAnimationBinding? = null
    private val binding get() = _binding!!

    lateinit var listObjects: MutableList<ImageView> //лист с объектами для анимации
    private val IMAGE_MARGIN = 64 // отсутпы от краёв экрана
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val duration = 1000L
            val startDelay = 500L

            //Выводим анимированную подсказку
            ViewCompat.animate(imageViewClick)
                .rotationBy(-15F)
                .setDuration(duration)
                .setInterpolator(CycleInterpolator(2F))
                .setStartDelay(startDelay).start()

            //Скрываем анимированную подсказку
            coroutineScope.launch {
                delay(duration + startDelay + 1000)
                binding.imageViewClick.visibility = View.INVISIBLE
            }

            //Инициализируем объекты для анимации
            listObjects = mutableListOf(imageViewEarthAnim, imageViewMarsAnim, imageViewSaturnAnim)
            //Запускаем подслушку для изменения положения элементов через ConstraintSet
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000L

            imageViewSolarSystem.setOnClickListener {
                ConstraintSet().apply {
                    clone(constraintAnimation)
                    changeConstrains(this)
                    TransitionManager.beginDelayedTransition(constraintAnimation, TransitionSet().addTransition(changeBounds))
                    applyTo(constraintAnimation)
                }
            }
        }
    }

    //Реализация логики анимации
    private fun changeConstrains(set: ConstraintSet) {
        //очистка привязок всех объектов
        for (obj in listObjects) {
            clearConstrains(set, obj)
        }

        //положение сверху по центру
        set.connect(listObjects[1].id, TOP, PARENT_ID, TOP, IMAGE_MARGIN)
        set.connect(listObjects[1].id, START, PARENT_ID, START)
        set.connect(listObjects[1].id, END, PARENT_ID, END)

        //положение внизу слева
        set.connect(listObjects[2].id, BOTTOM, PARENT_ID, BOTTOM, IMAGE_MARGIN)
        set.connect(listObjects[2].id, START, PARENT_ID, START, IMAGE_MARGIN)

        //положение внизу справа
        set.connect(listObjects[0].id, BOTTOM, PARENT_ID, BOTTOM, IMAGE_MARGIN)
        set.connect(listObjects[0].id, END, PARENT_ID, END, IMAGE_MARGIN)

        //инверсия объектов
        val bufer = listObjects[0]
        listObjects[0] = listObjects[1]
        listObjects[1] = listObjects[2]
        listObjects[2] = bufer
    }

    //Функция очищает все привязки во View
    private fun <T : View> clearConstrains(set: ConstraintSet, obj: T) {
        set.clear(obj.id, TOP)
        set.clear(obj.id, BOTTOM)
        set.clear(obj.id, START)
        set.clear(obj.id, END)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        _binding = null
    }
}