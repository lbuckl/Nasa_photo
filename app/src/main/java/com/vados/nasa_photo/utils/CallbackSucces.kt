package com.vados.nasa_photo.utils

import com.vados.nasa_photo.model.PictureDTO

interface CallbackSucces {
    fun setSucces(pictureDTO: PictureDTO)
}