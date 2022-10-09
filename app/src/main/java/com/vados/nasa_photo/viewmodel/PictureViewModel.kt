package com.vados.nasa_photo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vados.nasa_photo.model.PictureDTO
import com.vados.nasa_photo.model.retrofit.PictureRequestImpl
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import retrofit2.Call
import retrofit2.Response

/**
 * ViewModel реализующая бизнес-логику работы фрагмента для отображения
 * фотографии получаемой с сайта NASA
 */
class PictureViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
): ViewModel(){

    val getLiveData = {
        getPictureDTO()
        liveData
    }

    private fun getPictureDTO(){
        PictureRequestImpl.getRetrofitImpl().getPicture(NASA_PICTURE_API_KEY).enqueue(object :
            retrofit2.Callback<PictureDTO>{
            override fun onResponse(call: Call<PictureDTO>, response: Response<PictureDTO>) {
                Log.v("@@@", "VM:setSucces")
                liveData.postValue(AppState.Succes(response.body()!!))
            }
            override fun onFailure(call: Call<PictureDTO>, t: Throwable) {
                liveData.postValue(AppState.Error(Exception("Loading Failure")))
                Log.v("@@@", "Loading Failure")
            }
        })
    }
}