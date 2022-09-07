package com.vados.nasa_photo.ui.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.FragmentGreetingsBinding
import com.vados.nasa_photo.ui.picture.PictureOfTheDayFragment

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingGreetings.imageView.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance(),"POTD")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingGreetings = null
    }
}