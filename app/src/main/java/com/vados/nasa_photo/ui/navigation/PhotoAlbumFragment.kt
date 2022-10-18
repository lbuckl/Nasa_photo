package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb.weather.view.weatherlist.PhotoAlbumRecyclerAdapter
import com.vados.nasa_photo.databinding.FragmentPhotoalbumBinding
import com.vados.nasa_photo.utils.showSnackBarErrorMsg
import molchanov.hammertesttask.viewmodel.PhotoAlbumListAppState
import molchanov.hammertesttask.viewmodel.PhotoAlbumListViewModel

class PhotoAlbumFragment:Fragment() {

    companion object {
        lateinit var viewModel: PhotoAlbumListViewModel
        fun newInstance() = PhotoAlbumFragment()
    }
    var initConnection = false
    var isConnection = true

    private var _binding: FragmentPhotoalbumBinding? = null
    private val binding: FragmentPhotoalbumBinding
    get() {
        return _binding!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentPhotoalbumBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PhotoAlbumListViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
    }

    private fun renderData(photoAlbumListAppState: PhotoAlbumListAppState){
        when (photoAlbumListAppState){
            is PhotoAlbumListAppState.Succes ->{
                binding.progressBar.isVisible = false
                binding.recyclerview.adapter = PhotoAlbumRecyclerAdapter(photoAlbumListAppState.menuListDTO)
            }
            is PhotoAlbumListAppState.Error -> {
                binding.progressBar.isVisible = false
                view?.showSnackBarErrorMsg("Данные не загружены")
            }
            is PhotoAlbumListAppState.Loading -> {
                binding.progressBar.isVisible = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}