package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentAnimationBinding

class AnimationFragment:Fragment() {

    private var _binding: FragmentAnimationBinding? = null
    private val binding get() = _binding!!

    lateinit var listObjects:MutableList<Button>

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
            listObjects = mutableListOf(button1,button2,button3)
            buttonCenter.setOnClickListener {
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
        with(binding){


            //очистка привязок всех объектов
            for (obj in listObjects){
                clearConstrains(set,obj)
            }

            //положение сверху по центру
            set.connect(listObjects[1].id,TOP, PARENT_ID, TOP)
            set.connect(listObjects[1].id,START, PARENT_ID, START)
            set.connect(listObjects[1].id,END, PARENT_ID, END)

            //положение внизу слева
            set.connect(listObjects[2].id,BOTTOM, PARENT_ID, BOTTOM)
            set.connect(listObjects[2].id,START, PARENT_ID, START)

            //положение внизу справа
            set.connect(listObjects[0].id,BOTTOM, PARENT_ID, BOTTOM)
            set.connect(listObjects[0].id,END, PARENT_ID, END)

            //инверсия объектов
            val bufer = listObjects[2]
            listObjects[2] = listObjects[1]
            listObjects[1] = listObjects[0]
            listObjects[0] = bufer
        }
    }

    private fun clearConstrains(set:ConstraintSet, obj:Button){
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