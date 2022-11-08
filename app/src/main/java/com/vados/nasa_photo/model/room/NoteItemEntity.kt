package com.gb.weather.model.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Класс хранящий данные о заметке
 * @param id - id в БД room
 * @param header - оглавление
 * @param description - содержание
 * @param date - дата
 */
@Parcelize
@Entity
data class NoteItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val header: String,
    val description:String,
    val date: String
):Parcelable