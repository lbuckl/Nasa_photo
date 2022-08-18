package com.vados.nasa_photo.model.retrofit

import com.vados.nasa_photo.model.PictureDTO
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PictureRequestInterface {
    @GET("/v2/informers")
    fun getPicture(apiKey: String): Call<PictureDTO>
}