package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentAnimationBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimationFragment:Fragment() {

    private var _binding: FragmentAnimationBinding? = null
    private val binding get() = _binding!!

    lateinit var listObjects:MutableList<ImageView>
    private val IMAGE_MARGIN = 64

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

            GlobalScope.launch {
                delay(duration + startDelay + 1000)
                binding.imageViewClick.visibility = View.INVISIBLE
            }


            listObjects = mutableListOf(imageViewEarthAnim,imageViewMarsAnim,imageViewSaturnAnim)
            imageViewSolarSystem.setOnClickListener {
                ConstraintSet().apply {
                    clone(constraintAnimation)
                    changeConstrains(this)
                    TransitionManager.beginDelayedTransition(constraintAnimation)
                    applyTo(constraintAnimation)
                }
            }
        }
    }

    private fun changeConstrains(set:ConstraintSet){


            //очистка привязок всех объектов
            for (obj in listObjects){
                clearConstrains(set,obj)
            }

            //положение сверху по центру
            set.connect(listObjects[1].id,TOP, PARENT_ID, TOP,IMAGE_MARGIN)
            set.connect(listObjects[1].id,START, PARENT_ID, START)
            set.connect(listObjects[1].id,END, PARENT_ID, END)

            //положение внизу слева
            set.connect(listObjects[2].id,BOTTOM, PARENT_ID, BOTTOM,IMAGE_MARGIN)
            set.connect(listObjects[2].id,START, PARENT_ID, START,IMAGE_MARGIN)

            //положение внизу справа
            set.connect(listObjects[0].id,BOTTOM, PARENT_ID, BOTTOM,IMAGE_MARGIN)
            set.connect(listObjects[0].id,END, PARENT_ID, END,IMAGE_MARGIN)

            //инверсия объектов
            val bufer = listObjects[0]
            listObjects[0] = listObjects[1]
            listObjects[1] = listObjects[2]
            listObjects[2] = bufer
    }

    private fun <T:View> clearConstrains(set:ConstraintSet, obj:T){
        set.clear(obj.id,TOP)
        set.clear(obj.id, BOTTOM)
        set.clear(obj.id, START)
        set.clear(obj.id, END)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}