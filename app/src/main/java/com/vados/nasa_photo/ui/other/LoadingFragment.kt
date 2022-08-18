package com.vados.nasa_photo.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentLoadingBinding

class LoadingFragment: Fragment() {
    private lateinit var binding_load: FragmentLoadingBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding_load = FragmentLoadingBinding.inflate(inflater)
        return binding_load.root
    }
}