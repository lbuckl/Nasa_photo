package com.vados.nasa_photo.ui.notebook.callbacks

import com.gb.weather.model.room.NoteItemEntity

/**
 * Интерфейс коллбэка для добавления элемента в записную книжку
 */
fun interface ReplaceItemCallback {
    fun replace(item: NoteItemEntity)
}