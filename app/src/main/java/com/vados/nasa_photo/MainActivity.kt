package com.vados.nasa_photo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vados.nasa_photo.ui.picture.PictureOfTheDayFragment
import com.vados.nasa_photo.utils.PREF_SETTINGS
import com.vados.nasa_photo.utils.PREF_THEME_INT

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //Устанавливаем тему
        setTheme(getAppTheme())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    private fun getAppTheme():Int{
        Log.v("@@@",getCodeTheme().toString())
        return when (getCodeTheme()){
            0 -> R.style.Theme_Nasa_photo
            1 -> R.style.Theme_Dark
            2 -> R.style.Theme_Red
            3 -> R.style.Theme_Space
            else -> R.style.Theme_Nasa_photo
        }
    }

    private fun getCodeTheme():Int{
        val sharedPref = getSharedPreferences(PREF_SETTINGS,0)
        return sharedPref.getInt(PREF_THEME_INT,0)
    }
}