package com.gb.weather.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Абстрактный класс для работы с БД заметок
 */
@Database(entities = arrayOf(NoteItemEntity::class), version = 1)
abstract class NoteBookHistoryDatabase: RoomDatabase() {
    //Функция через которую осуществляется запрос в БД
    abstract fun weatherDao():NoteItemDao
}