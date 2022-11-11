package com.vados.nasa_photo.ui.greetings.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vados.nasa_photo.MainActivity
import com.vados.nasa_photo.databinding.ActivitySplashBinding
import com.vados.nasa_photo.ui.greetings.ViewPagerActivity
import com.vados.nasa_photo.utils.FIRST_ACTIVE
import com.vados.nasa_photo.utils.INITIALIZATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class MySplashActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


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
                delay(1500)
                startActivity(Intent(this@MySplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}