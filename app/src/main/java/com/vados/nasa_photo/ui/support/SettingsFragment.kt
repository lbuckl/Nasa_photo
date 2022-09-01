package com.vados.nasa_photo.ui.support

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
    private var isChose = false

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //инициализация чип группы для смены темы
        binding.chipGroupStyles.setOnCheckedStateChangeListener{chipGroup: ChipGroup,
                                                                mutableList: MutableList<Int> ->
            //Log.v("@@@",binding.chipLight.id.toString())
            //Log.v("@@@",chipGroup.checkedChipId.toString())

            when (chipGroup.checkedChipId){
                binding.chipLight.id -> setAppTheme(THEME_LIGHT)
                binding.chipDark.id -> setAppTheme(THEME_DARK)
                binding.chipRed.id -> setAppTheme(THEME_RED)
                binding.chipSpace.id -> setAppTheme(THEME_SPACE)
            }
        }

        //инициализация кнопки "Применить"
        binding.buttonApply.setOnClickListener {
            requireActivity().recreate()
        }
    }

    // Сохранение настроек стиля
    private fun setAppTheme(styleCode:Int){
        Log.v("@@@",styleCode.toString())
        val sharedPrefer = requireContext().getSharedPreferences(PREF_SETTINGS,Context.MODE_PRIVATE)
        val editor = sharedPrefer.edit()
        editor.putInt(PREF_THEME_INT,styleCode).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
