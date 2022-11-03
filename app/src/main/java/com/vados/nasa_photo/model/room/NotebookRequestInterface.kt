package com.vados.nasa_photo.model.room

import com.gb.weather.model.room.NoteItemEntity

interface NotebookRequestInterface {
    fun getHistoryList(): List<NoteItemEntity>
    fun addItemToHistory(item: NoteItemEntity)
    fun deleteItemFromHistory(item:NoteItemEntity)
    fun replaceItemInHistory(item:NoteItemEntity)
}