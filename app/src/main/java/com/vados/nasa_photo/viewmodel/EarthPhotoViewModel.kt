package com.vados.nasa_photo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import molchanov.hammertesttask.model.request.EarthRequestImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel реализующая бизнес-логику работы фрагмента для отображения
 * фотографии получаемой с сайта NASA
 */
class EarthPhotoViewModel(private val liveData: MutableLiveData<EarthPhotoAppState> = MutableLiveData<EarthPhotoAppState>()
): ViewModel(){

    val getLiveData = {
        getPictureDTO()
        liveData
    }

    private fun getPictureDTO(){
        EarthRequestImpl.getRetrofitImpl().getPicture("2022_09_10",NASA_PICTURE_API_KEY).enqueue(object :
            Callback<EarthPhotoDTO>{
            override fun onResponse(call: Call<EarthPhotoDTO>, response: Response<EarthPhotoDTO>){
                Log.v("@@@", "VM:setSucces")
                liveData.postValue(EarthPhotoAppState.Succes(response.body()!!))
            }
            override fun onFailure(call: Call<EarthPhotoDTO>, t: Throwable) {
                liveData.postValue(EarthPhotoAppState.Error(Exception("Loading Failure")))
                Log.v("@@@", "Loading Failure")
            }
        })
    }
}
