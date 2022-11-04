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

    //Вернуть отсортированный список
    override fun getHistoryList(): MutableList<NoteItemEntity> {
        return MyApp.getNotesFromDatabase().weatherDao().getEntityList()
    }

    fun clearHistory() {
        Thread{MyApp.getNotesFromDatabase().weatherDao().clearHistory()}.start()
    }

    //Заменяет местами записи в БД путём переставления местами id
    fun replaceItemPosition(fromPosition: Int, toPosition: Int){
        MyApp.getNotesFromDatabase().weatherDao().also {

        val list = if (fromPosition > toPosition) it.getEntityListLimited(toPosition,2)
        else it.getEntityListLimited(fromPosition,2)

        val buf = list[1].id
        list[1].id = list[0].id
        list[0].id = buf

        it.update(list[0])
        it.update(list[1])
        }
    }
}