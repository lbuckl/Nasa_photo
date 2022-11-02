package com.vados.nasa_photo.viewmodel.navigation

import com.vados.nasa_photo.model.dto.PictureDTO

sealed class POTDAppState {
    data class Success(val pictureDTO: PictureDTO): POTDAppState()
    data class Error(val error:Exception): POTDAppState()
    object Loading: POTDAppState()
}