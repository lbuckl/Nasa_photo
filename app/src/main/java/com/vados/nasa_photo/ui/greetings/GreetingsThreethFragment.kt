package com.vados.nasa_photo.ui.greetings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentGreetingsTreethBinding
import com.vados.nasa_photo.utils.FIRST_ACTIVE
import com.vados.nasa_photo.utils.INITIALIZATION

class GreetingsThreethFragment:Fragment() {

    private var _binding: FragmentGreetingsTreethBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsTreethBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Выставляем флаг, что пользователь просмотрел приветственную информацию
        binding.buttonStart.setOnClickListener {
            val sharedPrefer = requireContext().getSharedPreferences(INITIALIZATION, Context.MODE_PRIVATE)
            val editor = sharedPrefer.edit()
            editor.putBoolean(FIRST_ACTIVE,false).apply()
            requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}