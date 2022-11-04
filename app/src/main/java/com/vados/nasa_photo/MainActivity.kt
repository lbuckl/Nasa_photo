package com.vados.nasa_photo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.vados.nasa_photo.ui.greetings.GreetingsFragment
import com.vados.nasa_photo.ui.greetings.ViewPagerActivity
import com.vados.nasa_photo.ui.navigation.AnimationFragment
import com.vados.nasa_photo.ui.navigation.EarthPhotoFragment
import com.vados.nasa_photo.ui.navigation.PhotoAlbumFragment
import com.vados.nasa_photo.ui.navigation.PictureOfTheDayFragment
import com.vados.nasa_photo.utils.FIRST_ACTIVE
import com.vados.nasa_photo.utils.INITIALIZATION
import com.vados.nasa_photo.utils.getAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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
                            val lastFragment = supportFragmentManager.findFragmentByTag("POD_Fragment")
                            replaceFragment(lastFragment, PictureOfTheDayFragment(),"POD_Fragment")
                        }
                        1 ->{
                            val lastFragment = supportFragmentManager.findFragmentByTag("Earth_Fragment")
                            replaceFragment(lastFragment, EarthPhotoFragment(),"Earth_Fragment")
                        }
                        2 ->{
                            val lastFragment = supportFragmentManager.findFragmentByTag("Photo_Fragment")
                            replaceFragment(lastFragment, PhotoAlbumFragment(),"Photo_Fragment")
                        }
                        3 ->{
                            val lastFragment = supportFragmentManager.findFragmentByTag("Animation")
                            replaceFragment(lastFragment, AnimationFragment(),"Animation")
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