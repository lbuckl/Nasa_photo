package com.vados.nasa_photo.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb.weather.view.weatherlist.EarthPhotoRecyclerAdapter
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.FragmentEarthPhotoBinding
import com.vados.nasa_photo.ui.support.SettingsFragment
import com.vados.nasa_photo.viewmodel.EarthPhotoAppState
import com.vados.nasa_photo.viewmodel.EarthPhotoViewModel

/**
 * Фрагмент для отображения фотографий земли из API NASA
 */
class EarthPhotoFragment : Fragment() {

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
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthPhotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[EarthPhotoViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        setHasOptionsMenu(true)
        initBottomAppBar()
    }

    private fun renderData(photoAlbumListAppState: EarthPhotoAppState) {
        when (photoAlbumListAppState) {
            is EarthPhotoAppState.Succes -> {
                binding.recyclerview.adapter =
                    EarthPhotoRecyclerAdapter(photoAlbumListAppState.pictureDTO)
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

    //region BottomAppBar
    //Функция инициализирует и устанавливает логику работы BottomAppBar
    private fun initBottomAppBar() {
        binding.bottomAppBar.let {
            it.replaceMenu(R.menu.menu_bottom_bar)
            onMenuItemSelected(it.menu)
            it.setNavigationOnClickListener { itView ->
                val popupMenu = PopupMenu(context, itView)
                requireActivity().menuInflater.inflate(
                    R.menu.menu_bottom_navigation,
                    popupMenu.menu
                )
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.navigation_archive -> {
                            //TODO
                        }
                        R.id.navigation_send -> {
                            //TODO
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}