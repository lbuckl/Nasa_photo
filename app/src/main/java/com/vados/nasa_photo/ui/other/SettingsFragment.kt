package com.vados.nasa_photo.ui.other

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.vados.nasa_photo.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}