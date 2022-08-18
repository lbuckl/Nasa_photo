package com.vados.nasa_photo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vados.nasa_photo.model.retrofit.PictureRequestRetrofit

class PictureViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
): ViewModel() {

    val getLiveData = {
        liveData
    }

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getPictureDTO(){
        PictureRequestRetrofit.request()
    }
}