package com.vados.nasa_photo.model

import com.vados.nasa_photo.model.retrofit.PictureRequestRetrofit
import com.vados.nasa_photo.utils.CallbackError
import com.vados.nasa_photo.utils.CallbackSucces

class RepositoryRemoteImpl {

    fun getPictureDTO(callbackSucces: CallbackSucces,callbackError: CallbackError){
        Thread{
            val result = PictureRequestRetrofit.request()
            if (result != null) callbackSucces.setSucces(result)
            else callbackError.setError("Loading failure")
        }.start()
    }
}
