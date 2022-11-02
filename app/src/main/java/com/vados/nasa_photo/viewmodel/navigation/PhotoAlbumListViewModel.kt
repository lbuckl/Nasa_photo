package com.vados.nasa_photo.viewmodel.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.weather.domain.PhotoAlbumItem
import com.vados.nasa_photo.viewmodel.navigation.PhotoAlbumListAppState
import molchanov.hammertesttask.model.dto.MenuDTO
import molchanov.hammertesttask.model.request.PhotoAlbumRequestImpl
import retrofit2.Call
import retrofit2.Response

class PhotoAlbumListViewModel(private val liveData: MutableLiveData<PhotoAlbumListAppState> = MutableLiveData<PhotoAlbumListAppState>()) :
    ViewModel() {

    val getLiveData = {
        getPhotoAlbumItems()
        liveData
    }

    private fun getPhotoAlbumItems() {
        liveData.postValue(PhotoAlbumListAppState.Loading)
        PhotoAlbumRequestImpl.getRetrofitImpl().getPhotoAlbum(
            "earth",
            "earth",
            "russia",
            "image"
        ).enqueue(object : retrofit2.Callback<MenuDTO> {
            override fun onResponse(call: Call<MenuDTO>, response: Response<MenuDTO>) {
                try {
                    val listResponce = PhotoAlbumListAppState.Success(menuDTOtoListAlbumItem(response.body()!!))
                    liveData.postValue(listResponce)
                }catch (e:NullPointerException){
                    e.printStackTrace()
                    liveData.postValue(PhotoAlbumListAppState.Error(Exception("Loading Failure")))
                }
            }

            override fun onFailure(call: Call<MenuDTO>, t: Throwable) {
                liveData.postValue(PhotoAlbumListAppState.Error(Exception("Loading Failure")))
            }
        })
    }

    private fun menuDTOtoListAlbumItem(collection: MenuDTO): List<PhotoAlbumItem> {
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