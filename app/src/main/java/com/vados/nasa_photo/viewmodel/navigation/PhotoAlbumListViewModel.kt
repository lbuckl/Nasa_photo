package com.vados.nasa_photo.viewmodel.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.weather.domain.PhotoAlbumItem
import molchanov.hammertesttask.model.dto.AlbumDTO
import molchanov.hammertesttask.model.request.PhotoAlbumRequestImpl
import retrofit2.Call
import retrofit2.Response

class PhotoAlbumListViewModel(private val liveData: MutableLiveData<PhotoAlbumListAppState> = MutableLiveData<PhotoAlbumListAppState>()) :
    ViewModel() {

    val getLiveData = {
        getPhotoAlbumItems()
        liveData
    }

    //Функция получения данных фотоальбома NASA
    private fun getPhotoAlbumItems() {
        liveData.postValue(PhotoAlbumListAppState.Loading)
        PhotoAlbumRequestImpl.getRetrofitImpl().getPhotoAlbum(
            "earth",
            "earth",
            "russia",
            "image"
        ).enqueue(object : retrofit2.Callback<AlbumDTO> {
            //Действие при удачном получении данных
            override fun onResponse(call: Call<AlbumDTO>, response: Response<AlbumDTO>) {
                try {
                    val listResponce = PhotoAlbumListAppState.Success(menuDTOtoListAlbumItem(response.body()!!))
                    liveData.postValue(listResponce)
                }catch (e:NullPointerException){
                    e.printStackTrace()
                    liveData.postValue(PhotoAlbumListAppState.Error(Exception("Loading Failure")))
                }
            }
            //Действие при ошибке
            override fun onFailure(call: Call<AlbumDTO>, t: Throwable) {
                liveData.postValue(PhotoAlbumListAppState.Error(Exception("Loading Failure")))
            }
        })
    }

    //функция преобразующая данные в полученные от API в данные для отображения
    private fun menuDTOtoListAlbumItem(collection: AlbumDTO): List<PhotoAlbumItem> {
        val listMenu: MutableList<PhotoAlbumItem> = mutableListOf()

        for (item in collection.collection.items) {
            listMenu.add(
                PhotoAlbumItem(
                    item.data[0].center,
                    item.data[0].title,
                    item.links[0].href,
                )
            )
        }

        return listMenu
    }
}