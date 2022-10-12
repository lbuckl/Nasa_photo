package com.vados.nasa_photo.ui.greetings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentGreetingsBinding

class GreetingsFragment:Fragment(){
    private var _bindingGreetings: FragmentGreetingsBinding? = null
    private val bindingGreetings get() = _bindingGreetings!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingGreetings = FragmentGreetingsBinding.inflate(inflater)
        return bindingGreetings.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingGreetings = null
    }

}