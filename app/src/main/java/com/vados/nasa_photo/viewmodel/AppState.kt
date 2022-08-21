package com.vados.nasa_photo.viewmodel

import com.vados.nasa_photo.model.PictureDTO

sealed class AppState {
    data class Succes(val pictureDTO: PictureDTO): AppState()
    data class Error(val error:Exception): AppState()
    object Loading: AppState()
}