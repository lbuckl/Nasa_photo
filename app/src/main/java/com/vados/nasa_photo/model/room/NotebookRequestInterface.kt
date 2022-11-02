package com.vados.nasa_photo.model.room

import com.gb.weather.model.room.NoteItemEntity
import com.vados.nasa_photo.domain.NoteItem

interface NotebookRequestInterface {
    fun getHistoryList(): List<NoteItemEntity>
    fun addItemToHistory(item: NoteItemEntity)
    fun deleteItemFromHistory(item:NoteItemEntity)
    fun replaceItemInHistory(item:NoteItemEntity)
}