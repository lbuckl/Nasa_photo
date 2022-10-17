package com.vados.nasa_photo.model.retrofit

import com.vados.nasa_photo.model.dto.PictureDTO
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthPictureRequestInterface {
    @GET("EPIC/api//enhanced")
    fun getPicture(
        @Query("date") date:String,
        @Query("api_key") apiKey: String
    ): Call<EarthPhotoDTO>
}