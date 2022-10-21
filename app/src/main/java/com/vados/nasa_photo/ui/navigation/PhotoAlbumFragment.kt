package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb.weather.view.weatherlist.PhotoAlbumRecyclerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.FragmentPhotoalbumBinding
import com.vados.nasa_photo.ui.support.SettingsFragment
import com.vados.nasa_photo.utils.showSnackBarErrorMsg
import molchanov.hammertesttask.viewmodel.PhotoAlbumListAppState
import molchanov.hammertesttask.viewmodel.PhotoAlbumListViewModel

class PhotoAlbumFragment:Fragment() {

    companion object {
        lateinit var viewModel: PhotoAlbumListViewModel
        fun newInstance() = PhotoAlbumFragment()
    }

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

        //Инициализируем работу нижнего меню
        //скрываем меню
        setHasOptionsMenu(true)
        initBottomAppBar()
        initConstraintSet()
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

    //region BottomAppBar
    //Функция инициализирует и устанавливает логику работы BottomAppBar
    private fun initBottomAppBar() {
        binding.bottomAppBar.let {
            it.replaceMenu(R.menu.menu_bottom_bar)
            onMenuItemSelected(it.menu)
            it.setNavigationOnClickListener { itView ->
                val popupMenu = PopupMenu(context,itView)
                requireActivity().menuInflater.inflate(R.menu.menu_bottom_navigation, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.navigation_archive -> {
                        }
                        R.id.navigation_send -> {
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }

    //функция реализует логику работы по клику на иконки меню BottomAppBar
    private fun onMenuItemSelected(menu: Menu) {
        menu.findItem(R.id.app_bar_fav).isVisible = false
        menu.forEach {
            it.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.app_bar_settings -> {
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .hide(this)
                            .add(R.id.container, SettingsFragment.newInstance())
                            .addToBackStack("main")
                            .commit()
                    }
                }
                true
            }
        }
    }
    //endregion

    private fun initConstraintSet(){
        with(binding){
            AppBarAction().layoutDependsOn(mainContent,inputParametersLayout,photoAlbumToolbar)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}