package com.vados.nasa_photo.ui.notebook

import com.gb.weather.model.room.NoteItemEntity

/**
 * Интерфейс коллбэка для добавления элемента в записную книжку
 */
fun interface CBaddItem {
    fun add(item:NoteItemEntity)
}