package com.gb.weather.model.room

import androidx.room.*

@Dao
interface NoteItemDao {
    //region общие запросы
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(noteItemEntity: NoteItemEntity)

    @Update
    fun update(menuItemEntity: NoteItemEntity)

    @Delete
    fun delete(menuItemEntity: List<NoteItemEntity> )
    //endregion

    //Чистит всю базу
    @Query ("DELETE FROM NoteItemEntity")
    fun clearHistory()

    //Возвращает всю базу в обратном порядке
    @Query("SELECT * FROM NoteItemEntity ORDER BY id DESC")
    fun getEntityListInvert(): MutableList<NoteItemEntity>

    //Удаляет элемент по id
    @Query("DELETE FROM NoteItemEntity WHERE id = :idDelete")
    fun deleteItemById(idDelete: Long)

}