package com.vados.nasa_photo.model

import com.google.gson.annotations.SerializedName

/**
 * Класс хранящий данные о фото получаемые с сайта NASA
 */
data class PictureDTO (
        @field:SerializedName("media_type") val mediaType: String?, //тип данных "видео" или "изображение"
        @field:SerializedName("url") val url: String?, //URL для загрузки
        @field:SerializedName("explanation") val explanation: String?, // описание изображения
        @field:SerializedName("title") val title: String?, //Название
        @field:SerializedName("date") val date: String?, //Дата изображения
        @field:SerializedName("hdurl") val hdurl: String? //URL для загрузки в формате HD
)

//Notused
//@field:SerializedName("copyright") val copyright: String?, //кому пренадлежат права