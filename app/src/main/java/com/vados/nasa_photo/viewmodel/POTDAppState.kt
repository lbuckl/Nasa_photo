package com.vados.nasa_photo.viewmodel

import com.vados.nasa_photo.model.dto.PictureDTO

sealed class POTDAppState {
    data class Succes(val pictureDTO: PictureDTO): POTDAppState()
    data class Error(val error:Exception): POTDAppState()
    object Loading: POTDAppState()
}