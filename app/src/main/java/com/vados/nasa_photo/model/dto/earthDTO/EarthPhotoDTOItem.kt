package com.vados.nasa_photo.model.dto.earthDTO

/**
 * Класс харнящий данные о фото земли
 * @param caption - описание
 * @param date - дата фотографии
 * @param image - ссылка на изображение
 */
data class EarthPhotoDTOItem(
    val caption: String,
    val date: String,
    var image: String,
)