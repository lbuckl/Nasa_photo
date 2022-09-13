package com.vados.nasa_photo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vados.nasa_photo.ui.picture.PictureOfTheDayFragment
import com.vados.nasa_photo.ui.support.GreetingsFragment
import com.vados.nasa_photo.utils.PREF_SETTINGS
import com.vados.nasa_photo.utils.PREF_THEME_INT
import com.vados.nasa_photo.utils.THEME_LIGHT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //Устанавливаем тему
        setTheme(getAppTheme())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GreetingsFragment())
                .commitNow()
        }

        GlobalScope.launch {
            delay(2000)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance(),"POTD")
                .commit()
        }
    }

    private fun getAppTheme():Int{
        return when (getCodeTheme()){
            0 -> R.style.Theme_Nasa_photo
            1 -> R.style.Theme_Dark
            2 -> R.style.Theme_Red
            3 -> R.style.Theme_Space
            else -> R.style.Theme_Nasa_photo
        }
    }

    private fun getCodeTheme():Int{
        val sharedPref = getSharedPreferences(PREF_SETTINGS,Context.MODE_PRIVATE)
        return sharedPref.getInt(PREF_THEME_INT, THEME_LIGHT)
    }
}