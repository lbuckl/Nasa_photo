package com.gb.weather.model.room

import androidx.room.*

/**
 * Интерфейс работы с БД SQLite посредствам Room
 */
@Dao
interface NoteItemDao {
    //region Добавление элементов
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(noteItemEntity: NoteItemEntity)
    //endregion_______________________________________________________

    //region Изменение элементов
    @Update // Изменяет Entity элемент
    fun update(menuItemEntity: NoteItemEntity)
    //endregion_______________________________________________________

    //region Удаление элементов
    @Delete // удаляет коркретный Entity элемент
    fun delete(menuItemEntity: List<NoteItemEntity> )

    /**
     * Удаляет элемент по id
     * @param idDelete - id элемента по которому будет удалён элемент
     */
    @Query("DELETE FROM NoteItemEntity WHERE id = :idDelete")
    fun deleteItemById(idDelete: Long)

    //Чистит всю базу
    @Query ("DELETE FROM NoteItemEntity")
    fun clearHistory()
    //endregion_______________________________________________________

    //region Запрос элементов
    //Возвращает всю базу в прямом порядке
    @Query("SELECT * FROM NoteItemEntity ORDER BY id")
    fun getEntityList(): MutableList<NoteItemEntity>

    /**
     * Возвращает выборку со смещением
     * @param offset - смещение запроса (от какого элемента начать выборку)
     * @param amount - колличество возвращаемых элементов
     */
    @Query("SELECT * FROM NoteItemEntity LIMIT :offset,:amount")
    fun getEntityListLimited(offset:Int,amount:Int): MutableList<NoteItemEntity>
    //endregion_______________________________________________________
}