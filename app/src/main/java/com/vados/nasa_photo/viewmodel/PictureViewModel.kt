package com.vados.nasa_photo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vados.nasa_photo.model.PictureDTO
import com.vados.nasa_photo.model.RepositoryRemoteImpl
import com.vados.nasa_photo.utils.CallbackError
import com.vados.nasa_photo.utils.CallbackSucces

class PictureViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
): ViewModel(),CallbackSucces,CallbackError {
    var repository = RepositoryRemoteImpl()

    val getLiveData = {
        getPictureDTO()
        liveData
    }

    private fun getPictureDTO(){
        Log.d("@@@", "VM:getPictureDTO")
        repository.getPictureDTO(this,this)
        liveData.postValue(AppState.Loading)
    }

    override fun setError(errorMsg: String) {
        liveData.postValue(AppState.Error(Exception(errorMsg)))
        Log.d("@@@", errorMsg)
    }

    override fun setSucces(pictureDTO: PictureDTO) {
        Log.d("@@@", "VM:setSucces")
        //val result = PictureDTO("video",pictureDTO.url)
        //liveData.postValue(AppState.Succes(result))
        liveData.postValue(AppState.Succes(pictureDTO))
    }
}