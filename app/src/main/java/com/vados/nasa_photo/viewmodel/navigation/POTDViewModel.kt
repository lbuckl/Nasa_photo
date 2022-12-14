package com.vados.nasa_photo.viewmodel.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vados.nasa_photo.model.dto.PictureDTO
import com.vados.nasa_photo.model.retrofit.NasaRequestImpl
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import retrofit2.Call
import retrofit2.Response

/**
 * ViewModel реализующая бизнес-логику работы фрагмента для отображения
 * фотографии получаемой с сайта NASA
 */
class POTDViewModel(private val liveData: MutableLiveData<POTDAppState> = MutableLiveData<POTDAppState>()
): ViewModel(){

    val getLiveData = {
        getPictureDTO()
        liveData
    }

    //Функция получения фото дня
    private fun getPictureDTO(){
        if (NASA_PICTURE_API_KEY.isNotEmpty()){
            NasaRequestImpl.getRetrofitImpl().getPictureOfTheDay(NASA_PICTURE_API_KEY).enqueue(object :
                retrofit2.Callback<PictureDTO>{
                //Действие при удачном получении данных
                override fun onResponse(call: Call<PictureDTO>, response: Response<PictureDTO>){
                    try {
                        liveData.postValue(POTDAppState.Success(response.body()!!))
                    }catch (e:NullPointerException){
                        e.printStackTrace()
                        liveData.postValue(POTDAppState.Error(Exception("Loading Failure")))
                    }

                }
                //Действие при ошибке
                override fun onFailure(call: Call<PictureDTO>, t: Throwable) {
                    liveData.postValue(POTDAppState.Error(Exception("Loading Failure")))
                }
            })
        }
        else {
            liveData.postValue(POTDAppState.Error(Exception("Key API Error")))
        }
    }
}