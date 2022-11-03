package com.gb.weather.model

import com.gb.weather.model.room.NoteItemEntity
import com.vados.nasa_photo.MyApp
import com.vados.nasa_photo.model.room.NotebookRequestInterface

object NotebookRepository: NotebookRequestInterface {

    override fun addItemToHistory(item: NoteItemEntity) {
        MyApp.getNotesFromDatabase().weatherDao().insert(item)
    }

    override fun replaceItemInHistory(item: NoteItemEntity) {
        MyApp.getNotesFromDatabase().weatherDao().update(item)
    }

    override fun deleteItemFromHistory(item: NoteItemEntity) {
        return MyApp.getNotesFromDatabase().weatherDao().deleteItemById(item.id)
    }

    //Вернуть список отсортированный в порядке обратном добавлению объектов
    override fun getHistoryList(): MutableList<NoteItemEntity> {
        return MyApp.getNotesFromDatabase().weatherDao().getEntityListInvert()
    }

    fun clearHistory() {
        Thread{MyApp.getNotesFromDatabase().weatherDao().clearHistory()}.start()
    }

    fun repalceItemPosition(){

    }
}