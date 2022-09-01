package com.vados.nasa_photo.ui.support

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.vados.nasa_photo.databinding.FragmentPictureOfTheDayBinding
import com.vados.nasa_photo.databinding.FragmentSettingsBinding
import com.vados.nasa_photo.ui.picture.PictureOfTheDayFragment
import com.vados.nasa_photo.utils.*
import com.vados.nasa_photo.viewmodel.PictureViewModel
import kotlin.properties.Delegates

class SettingsFragment:Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var appTheme:Int = 0
    lateinit var sharedPref:SharedPreferences

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        initialization()
        return binding.root
    }

    private fun initialization(){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroupStyles.setOnCheckedStateChangeListener{chipGroup: ChipGroup,
                                                                mutableList: MutableList<Int> ->
            when (chipGroup.checkedChipId){
                mutableList[0] -> setAppTheme(THEME_LIGHT)
                mutableList[1] -> setAppTheme(THEME_DARK)
                mutableList[2] -> setAppTheme(THEME_RED)
                mutableList[3] -> setAppTheme(THEME_SPACE)
            }
        }
    }

    // Сохранение настроек стиля
    private fun setAppTheme(styleCode:Int){
        requireContext().getSharedPreferences(PREF_SETTNGS,0).apply {
            edit().putInt(PREF_THEME_INT,styleCode).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
