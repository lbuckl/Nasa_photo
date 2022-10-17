package com.vados.nasa_photo.viewmodel

import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO

sealed class EarthPhotoAppState {
    data class Succes(val pictureDTO: EarthPhotoDTO): EarthPhotoAppState()
    data class Error(val error:Exception): EarthPhotoAppState()
    object Loading: EarthPhotoAppState()
}