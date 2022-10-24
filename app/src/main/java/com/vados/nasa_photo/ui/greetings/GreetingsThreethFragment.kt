package com.vados.nasa_photo.ui.greetings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.vados.nasa_photo.databinding.FragmentGreetingsTreethBinding
import com.vados.nasa_photo.utils.FIRST_ACTIVE
import com.vados.nasa_photo.utils.INITIALIZATION
import com.vados.nasa_photo.utils.VISIBLE_DELAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Третий из трёх приветственных фрагментов
 * всплывает только при первом запуске
 */
class GreetingsThreethFragment : Fragment() {

    private var _binding: FragmentGreetingsTreethBinding? = null
    private val binding get() = _binding!!
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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
            val sharedPrefer =
                requireContext().getSharedPreferences(INITIALIZATION, Context.MODE_PRIVATE)
            val editor = sharedPrefer.edit()
            editor.putBoolean(FIRST_ACTIVE, false).apply()
            requireActivity().finish()
        }
    }

    fun setTextVisible(){
        if (!binding.buttonStart.isVisible){
            coroutineScope.launch {
                val fade = Fade().setDuration(VISIBLE_DELAY)
                TransitionManager.beginDelayedTransition(binding.root,fade)
                binding.textView.visibility = View.VISIBLE
                delay(2000)
                binding.buttonStart.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}