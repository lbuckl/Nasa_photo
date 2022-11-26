package com.vados.nasa_photo.viewmodel.navigation

import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTOItem

sealed class EarthPhotoAppState {
    data class Success(val pictureDTO: ArrayList<EarthPhotoDTOItem>): EarthPhotoAppState()
    data class Error(val error:Exception): EarthPhotoAppState()
    object Loading: EarthPhotoAppState()
}