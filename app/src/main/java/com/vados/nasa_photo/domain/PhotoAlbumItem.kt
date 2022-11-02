package com.gb.weather.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Класс хранящий данные элементов меню
 * @param name - намиенование фото
 * @param description - описание фото
 * @param link - ссылка на фото
 */
@Parcelize
data class PhotoAlbumItem(
    val name: String,
    val description: String,
    val link: String,
): Parcelable