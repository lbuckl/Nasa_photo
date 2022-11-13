package com.vados.nasa_photo.ui.greetings.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.vados.nasa_photo.MainActivity
import com.vados.nasa_photo.databinding.ActivitySplashBinding
import com.vados.nasa_photo.ui.greetings.ViewPagerActivity
import com.vados.nasa_photo.utils.FIRST_ACTIVE
import com.vados.nasa_photo.utils.INITIALIZATION
import com.vados.nasa_photo.utils.VISIBLE_DELAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class MySplashActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val animationDelay = 1000L
    private val visibleDelay = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Проверяем показывали ли мы приветственное окно
        val isFirstActive = getSharedPreferences(INITIALIZATION, Context.MODE_PRIVATE)
        if (isFirstActive.getBoolean(FIRST_ACTIVE, true)){
            startActivity(Intent(this, ViewPagerActivity::class.java))
        }
        else{

            coroutineScope.launch {
                val fade = Fade().setDuration(visibleDelay)
                delay(animationDelay)
                TransitionManager.beginDelayedTransition(binding.root,fade)
                binding.imageViewStarOne.visibility = View.VISIBLE

                delay(animationDelay)
                TransitionManager.beginDelayedTransition(binding.root,fade)
                binding.imageViewStarTwo.visibility = View.VISIBLE

                delay(animationDelay)
                TransitionManager.beginDelayedTransition(binding.root,fade)
                binding.imageViewStarThree.visibility = View.VISIBLE

                delay(animationDelay)
                TransitionManager.beginDelayedTransition(binding.root,fade)
                binding.textGreetings.visibility = View.VISIBLE
                delay(animationDelay)

                startActivity(Intent(this@MySplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}