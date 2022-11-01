package com.vados.nasa_photo.ui.greetings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.vados.nasa_photo.databinding.ActivityViewPagerBinding

/**
 * ViewPager для работы приветственных фрагментов
 */
class ViewPagerActivity: AppCompatActivity() {
    private var _binding:ActivityViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Создаю фрагменты для пролистывания
        val greetingsFirst= GreetingsFirstFragment()
        val greetingsSecond = GreetingsSecondFragment()
        val greetingsTreeth = GreetingsThreethFragment()

        val fragments = arrayOf(
            greetingsFirst, greetingsSecond, greetingsTreeth)

        //Инициализируем адаптер и навешиваем лисенер по которому будет отображаться анимация
        binding.viewPager.let { it ->
            it.adapter = ViewPagerAdapter(this,fragments)
            it.registerOnPageChangeCallback(
                object: ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        when(position){
                            1 -> {
                                greetingsSecond.setTextVisible()
                            }
                            2 -> {
                                greetingsTreeth.setTextVisible()
                            }
                        }
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}