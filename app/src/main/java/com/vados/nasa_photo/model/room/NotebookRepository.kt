package com.gb.weather.model

import com.gb.weather.model.room.NoteItemEntity
import com.vados.nasa_photo.MyApp
import com.vados.nasa_photo.model.room.NotebookRequestInterface

object NotebookRepository: NotebookRequestInterface {

    //Вернуть список отсортированный в порядке обратном добавлению объектов
    override fun getHistoryList(): List<NoteItemEntity> {
        return MyApp.getNotesFromDatabase().weatherDao().getEntityListInvert()
    }

    override fun addItemToHistory(menuItemList: List<NoteItemEntity>) {
        //TODO("Not yet implemented")
    }

    override fun deleteItemFromHistory(item: NoteItemEntity) {
        return MyApp.getNotesFromDatabase().weatherDao().insert(item)
    }

    override fun replaceItemInHistory(item: NoteItemEntity) {
        //TODO("Not yet implemented")
    }

    fun clearHistory() {
        Thread{MyApp.getNotesFromDatabase().weatherDao().clearHistory()}.start()
    }
}