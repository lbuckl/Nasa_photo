package com.gb.weather.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Класс хранящий данные элементов меню
 * @param name - намиенование блюда
 * @param description - описание блюда
 * @param link - ссылка на фото блюда
 * @param price - цена блюда
 */
@Parcelize
data class MenuItem(
    val name: String,
    val description: String,
    val link: String,
    var price: Double
): Parcelable