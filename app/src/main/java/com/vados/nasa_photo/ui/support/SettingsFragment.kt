package com.vados.nasa_photo.ui.support

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.vados.nasa_photo.R

/**
 * Фрагмент отображения меню настроек
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}