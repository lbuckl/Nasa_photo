package com.vados.nasa_photo.viewmodel.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTOItem
import com.vados.nasa_photo.model.retrofit.NasaRequestImpl
import com.vados.nasa_photo.utils.DayTimeData
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import com.vados.nasa_photo.utils.PAST_BIAS_DAY
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

    //Функция получения фотографий земли
    private fun getPictureDTO(){
        if (NASA_PICTURE_API_KEY.isNotEmpty()){
            liveData.postValue(EarthPhotoAppState.Loading)
            NasaRequestImpl.getRetrofitImpl()
                .getEarthPicture(pastDay.getPastDateWithDash(PAST_BIAS_DAY),NASA_PICTURE_API_KEY)
                .enqueue(object : Callback<ArrayList<EarthPhotoDTOItem>>{
                    //Действие при удачном получении данных
                    override fun onResponse(call: Call<ArrayList<EarthPhotoDTOItem>>, response: Response<ArrayList<EarthPhotoDTOItem>>){
                        try {
                            val result = replaceLinksInArray(response.body()!!)
                            liveData.postValue(EarthPhotoAppState.Success(result))
                        }catch (e:NullPointerException){
                            e.printStackTrace()
                            liveData.postValue(EarthPhotoAppState.Error(Exception("Loading Failure")))
                        }

                    }
                    //Действие при ошибке
                    override fun onFailure(call: Call<ArrayList<EarthPhotoDTOItem>>, t: Throwable) {
                        liveData.postValue(EarthPhotoAppState.Error(Exception("Loading Failure")))
                    }
                })
        }else {
            liveData.postValue(EarthPhotoAppState.Error(Exception("Key API Error")))
        }
    }

    /**
     * в параметре "image" NASA высылает толко название фото.
     * Функция реализует склейку прямой ссылки на фото по дате и названию
     */
    fun replaceLinksInArray(list: ArrayList<EarthPhotoDTOItem>):ArrayList<EarthPhotoDTOItem>{
        for(item in list){
            val date = item.date.split(" ")[0].replace("-","/")
            item.image = "https://epic.gsfc.nasa.gov/archive/enhanced/$date/png/${item.image}.png"
        }
        return list
    }
}