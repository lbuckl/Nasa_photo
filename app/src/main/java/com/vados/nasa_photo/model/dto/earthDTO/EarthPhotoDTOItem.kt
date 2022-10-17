package com.vados.nasa_photo.model.dto.earthDTO

data class EarthPhotoDTOItem(
    val caption: String, // Описание
    val date: String, // Дата
    val image: String, // Относительная ссылка на фото
)