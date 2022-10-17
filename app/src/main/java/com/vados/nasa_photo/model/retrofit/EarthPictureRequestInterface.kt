package com.vados.nasa_photo.model.retrofit

import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import retrofit2.Call
import retrofit2.http.*

interface EarthPictureRequestInterface {
    @GET("EPIC/api/enhanced/date/{date}")
    fun getPicture(
        @Path("date") date:String,
        @Query("api_key") apiKey: String
    ): Call<EarthPhotoDTO>
}