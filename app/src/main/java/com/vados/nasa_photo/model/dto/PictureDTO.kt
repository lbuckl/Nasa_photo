package com.vados.nasa_photo.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Класс хранящий данные о фото получаемые с сайта NASA
 * @param mediaType - тип данных "видео" или "изображение"
 * @param url - URL для загрузки
 * @param explanation - описание изображения
 * @param title - название
 * @param date - дата изображения
 * @param hdurl - URL для загрузки в формате HD
 */
data class PictureDTO (
        @field:SerializedName("media_type") val mediaType: String?,
        @field:SerializedName("url") val url: String?,
        @field:SerializedName("explanation") val explanation: String?,
        @field:SerializedName("title") val title: String?,
        @field:SerializedName("date") val date: String?,
        @field:SerializedName("hdurl") val hdurl: String?
)
