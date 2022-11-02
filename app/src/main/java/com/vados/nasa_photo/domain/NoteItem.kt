package com.vados.nasa_photo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Класс хранящий данные заметок
 * @param header - намиенование блюда
 * @param description - описание блюда
 * @param date - дата заметки
 */

@Parcelize
data class NoteItem (
    val header: String,
    val description:String,
    val date: String
    ):Parcelable