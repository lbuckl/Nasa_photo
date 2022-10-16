package com.vados.nasa_photo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.vados.nasa_photo.ui.greetings.GreetingsFragment
import com.vados.nasa_photo.ui.greetings.ViewPagerActivity
import com.vados.nasa_photo.ui.picture.PictureOfTheDayFragment
import com.vados.nasa_photo.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var isFirstActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        //Устанавливаем тему
        setTheme(getAppTheme())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Проверяем показывали ли мы приветственное окно
        val isFirstActive = getSharedPreferences(INITIALIZATION,Context.MODE_PRIVATE)
        if (isFirstActive.getBoolean(FIRST_ACTIVE, true)){
            startActivity(Intent(this, ViewPagerActivity::class.java))
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance(),"POTD")
                .commit()
        }
        else{
            runGreetings(savedInstanceState)
            initNavigation()
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

    private fun runGreetings(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GreetingsFragment())
                .commitNow()
        }

        GlobalScope.launch {
            delay(1500)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance(),"POTD")
                .commit()
        }
    }

    private fun initNavigation(){
        findViewById<TabLayout>(R.id.tab_layout)
            .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position){
                        0 -> {
                            val podLastFragment = supportFragmentManager.findFragmentByTag("POD_Fragment")
                            replaceFragment(podLastFragment,PictureOfTheDayFragment(),"POD_Fragment")
                        }
                        1 ->{
                            val marsLastFragment = supportFragmentManager.findFragmentByTag("Mars_Fragment")
                            replaceFragment(marsLastFragment,PictureOfTheDayFragment(),"POD_Fragment")
                        }
                        2 ->{
                            val podLastFragment = supportFragmentManager.findFragmentByTag("Photo_Fragment")
                            replaceFragment(podLastFragment,PictureOfTheDayFragment(),"POD_Fragment")
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    //TODO("Not yet implemented")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    //TODO("Not yet implemented")
                }
            })
    }

    /**
     * Функция для замены фрагмента в контейнере
     * если фрагмент ещё живой, то возвращает его в контейнер
     * если фрагмента нет, то создаёт новый
     */
    private fun replaceFragment(findFragment: Fragment?, newFragment: Fragment, flag:String){
        supportFragmentManager.beginTransaction().apply {
            if (findFragment == null) {
                replace(R.id.container, newFragment, flag)
                    .addToBackStack("Contacts")
                    .commitAllowingStateLoss()
            } else {
                replace(R.id.container, findFragment, flag)
                    .commitAllowingStateLoss()
            }
        }
    }
}