package com.vados.nasa_photo.model.retrofit

import com.vados.nasa_photo.model.dto.PictureDTO
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import molchanov.hammertesttask.model.dto.MenuDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Интерфейс запроса данных с API NASA
 * @param apiKey - ключ API NASA
 *
 * @param date - дата фото, должна приходить в запросе
 * @param apiKey - ключ API NASA
 *
 * @param q - основая тема звпроса
 * @param description - доп описание запроса
 * @param keywords - ключевые слова (если имеются)
 * @param media_type - тип медиафайла
 */
interface NasaRequestInterface {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureDTO>

    @GET("EPIC/api/enhanced/date/{date}")
    fun getEarthPicture(
        @Path("date") date:String,
        @Query("api_key") apiKey: String
    ): Call<EarthPhotoDTO>

    @GET("search")
    fun getPhotoAlbum(
        @Query("q") q: String,
        @Query("description") description: String,
        @Query("keywords") keywords: String,
        @Query("media_type") media_type: String,
    ): Call<MenuDTO>
}