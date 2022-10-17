package com.vados.nasa_photo.utils

import com.vados.nasa_photo.model.dto.PictureDTO

interface CallbackSucces {
    fun setSucces(pictureDTO: PictureDTO)
}