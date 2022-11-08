package com.vados.nasa_photo.model.room

import com.gb.weather.model.room.NoteItemEntity

/**
 * Интерфейс с минимальным набором функций для работы с БД заметок
 */
interface NotebookRequestInterface {
    //Команда вернуть список заметок
    fun getHistoryList(): List<NoteItemEntity>

    //Функция добавления заметки в историю
    fun addItemToHistory(item: NoteItemEntity)

    //Функция удаления заметки из истории
    fun deleteItemFromHistory(item:NoteItemEntity)

    //Функция изменения заметки в истории
    fun replaceItemInHistory(item:NoteItemEntity)
}