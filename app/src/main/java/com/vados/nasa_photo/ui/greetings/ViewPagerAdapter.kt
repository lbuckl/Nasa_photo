package com.vados.nasa_photo.ui.greetings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.vados.nasa_photo.databinding.FragmentGreetingsTreethBinding

/**
 * Адаптер ViewPager для работы приветственных фрагментов
 */
class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val fragments: Array<Fragment>):
    FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val WEATHER_FRAGMENT = 2
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                return fragments[EARTH_FRAGMENT]
            }
            1 -> {
               return fragments[MARS_FRAGMENT]
            }
            2 -> {
                return fragments[WEATHER_FRAGMENT]
            }
            else -> return fragments[EARTH_FRAGMENT]
        }
    }
}