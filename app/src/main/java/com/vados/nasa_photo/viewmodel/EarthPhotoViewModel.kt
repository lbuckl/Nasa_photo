package com.vados.nasa_photo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vados.nasa_photo.domain.DayTimeData
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import com.vados.nasa_photo.utils.PAST_BIAS_DAY
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
    val pastDay = DayTimeData()

    val getLiveData = {
        getPictureDTO()
        liveData
    }

    private fun getPictureDTO(){
        liveData.postValue(EarthPhotoAppState.Loading)
        EarthRequestImpl.getRetrofitImpl()
            .getPicture(pastDay.getPastDateWithDash(PAST_BIAS_DAY),NASA_PICTURE_API_KEY)
            .enqueue(object : Callback<EarthPhotoDTO>{
            override fun onResponse(call: Call<EarthPhotoDTO>, response: Response<EarthPhotoDTO>){
                Log.v("@@@", "VM:setSucces")
                val result = replaceLinksInArray(response.body()!!)
                liveData.postValue(EarthPhotoAppState.Succes(result))
            }
            override fun onFailure(call: Call<EarthPhotoDTO>, t: Throwable) {
                liveData.postValue(EarthPhotoAppState.Error(Exception("Loading Failure")))
                Log.v("@@@", "Loading Failure")
            }
        })
    }

    /**
     * в параметре "image" NASA высылает толко название фото.
     * Функция реализует склейку прямой ссылки на фото по дате и названию
     */
    private fun replaceLinksInArray(list: EarthPhotoDTO):EarthPhotoDTO{
        //val date = pastDay.getPastDateWithSlash(PAST_BIAS_DAY)
        for(item in list){
            val date = item.date.split(" ")[0].replace("-","/")
            item.image = "https://epic.gsfc.nasa.gov/archive/enhanced/$date/png/${item.image}.png"
        }
        return list
    }
}