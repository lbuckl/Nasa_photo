package com.vados.nasa_photo.ui.support

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.FragmentSettingsBinding
import com.vados.nasa_photo.utils.*

class SettingsFragment:Fragment() {

    private var _bindingSettings: FragmentSettingsBinding? = null
    private val bindingSettings get() = _bindingSettings!!
    private var isChose = false

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingSettings = FragmentSettingsBinding.inflate(inflater)
        return bindingSettings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
        //инициализация чип группы для смены темы
        bindingSettings.chipGroupStyles.setOnCheckedStateChangeListener{
                chipGroup: ChipGroup,
                mutableList: MutableList<Int> ->
            with(bindingSettings){
                when (chipGroup.checkedChipId){
                    chipLight.id -> setAppTheme(THEME_LIGHT)
                    chipDark.id -> setAppTheme(THEME_DARK)
                    chipRed.id -> setAppTheme(THEME_RED)
                    chipSpace.id -> setAppTheme(THEME_SPACE)
                    else -> {}
                }
            }
        }

        //инициализация кнопки "Применить"
        bindingSettings.buttonApply.setOnClickListener {
            requireActivity().recreate()
        }
    }

    private fun initialization(){
        when (getCodeTheme()){
            THEME_LIGHT -> bindingSettings.chipLight.isChecked = true
            THEME_DARK -> bindingSettings.chipDark.isChecked = true
            THEME_RED-> bindingSettings.chipRed.isChecked = true
            THEME_SPACE -> bindingSettings.chipSpace.isChecked = true
        }
    }

    // Сохранение настроек стиля
    private fun setAppTheme(styleCode:Int){
        val sharedPrefer = requireContext().getSharedPreferences(PREF_SETTINGS,Context.MODE_PRIVATE)
        val editor = sharedPrefer.edit()
        editor.putInt(PREF_THEME_INT,styleCode).apply()
    }

    private fun getCodeTheme():Int{
        val sharedPref = requireContext().getSharedPreferences(PREF_SETTINGS,Context.MODE_PRIVATE)
        return sharedPref.getInt(PREF_THEME_INT, THEME_LIGHT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSettings = null
    }
}
