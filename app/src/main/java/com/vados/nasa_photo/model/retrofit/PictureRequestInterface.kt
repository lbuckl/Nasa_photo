package com.vados.nasa_photo.model.retrofit

import com.vados.nasa_photo.model.PictureDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureRequestInterface {
    @GET("planetary/apod")
    fun getPicture(@Query("api_key") apiKey: String): Call<PictureDTO>
}