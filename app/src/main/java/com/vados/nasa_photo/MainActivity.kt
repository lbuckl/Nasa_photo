package com.vados.nasa_photo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }


    /**
     * Функция для замены фрагмента в контейнере
     * если фрагмент ещё живой, то возвращает его в контейнер
     * если фрагмента нет, то создаёт новый
     */
    private fun replaceFragment(findFragment: Fragment?, newFragment: Fragment, comint:String){
        supportFragmentManager.beginTransaction().apply {
            if (findFragment == null) {
                replace(R.id.container, newFragment, comint)
                    .addToBackStack("Contacts")
                    .commitAllowingStateLoss()
            } else {
                replace(R.id.container, findFragment, comint)
                    .commitAllowingStateLoss()
            }
        }
    }
}