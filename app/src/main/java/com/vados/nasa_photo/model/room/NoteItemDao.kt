package com.gb.weather.model.room

import androidx.room.*

@Dao
interface NoteItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(noteItemEntity: NoteItemEntity)

    @Update
    fun update(menuItemEntity: NoteItemEntity)

    @Delete
    fun delete(menuItemEntity: List<NoteItemEntity> )

    //Чистит всю базу
    @Query ("DELETE FROM NoteItemEntity")
    fun clearHistory()

    //Возвращает всю базу в обратном порядке
    @Query("SELECT * FROM NoteItemEntity ORDER BY id")
    fun getEntityList(): MutableList<NoteItemEntity>

    //Возвращает всю базу в обратном порядке
    @Query("SELECT * FROM NoteItemEntity ORDER BY id DESC")
    fun getEntityListInvert(): MutableList<NoteItemEntity>

    /**
     * Возвращает выборку со смещением
     * @param offset - смещение запроса (от какого элемента начать выборку)
     * @param amount - колличество возвращаемых элементов
     */
    @Query("SELECT * FROM NoteItemEntity LIMIT :offset,:amount")
    fun getEntityListLimited(offset:Int,amount:Int): MutableList<NoteItemEntity>

    //Удаляет элемент по id
    @Query("DELETE FROM NoteItemEntity WHERE id = :idDelete")
    fun deleteItemById(idDelete: Long)

}