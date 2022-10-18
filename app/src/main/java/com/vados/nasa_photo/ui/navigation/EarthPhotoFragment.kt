package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb.weather.view.weatherlist.EarthPhotoRecyclerAdapter
import com.vados.nasa_photo.databinding.FragmentEarthPhotoBinding
import com.vados.nasa_photo.viewmodel.EarthPhotoAppState
import com.vados.nasa_photo.viewmodel.EarthPhotoViewModel
import com.vados.nasa_photo.viewmodel.POTDViewModel

class EarthPhotoFragment:Fragment() {

    companion object {
        lateinit var viewModel: EarthPhotoViewModel
    }

    private var _binding: FragmentEarthPhotoBinding? = null
    private val binding: FragmentEarthPhotoBinding
    get() {
        return _binding!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentEarthPhotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[EarthPhotoViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
    }

    private fun renderData(photoAlbumListAppState: EarthPhotoAppState){
        when (photoAlbumListAppState){
            is EarthPhotoAppState.Succes ->{
                binding.recyclerview.adapter = EarthPhotoRecyclerAdapter(photoAlbumListAppState.pictureDTO)
                binding.progressBar.isVisible = false
            }
            is EarthPhotoAppState.Error -> {
                binding.progressBar.isVisible = false
            }
            is EarthPhotoAppState.Loading -> {
                binding.progressBar.isVisible = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}