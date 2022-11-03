package com.vados.nasa_photo.ui.notebook

import com.gb.weather.model.room.NoteItemEntity

fun interface RemoveItemCB {
    fun remove(item:NoteItemEntity)
}