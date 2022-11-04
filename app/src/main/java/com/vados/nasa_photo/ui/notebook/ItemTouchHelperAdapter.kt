package com.vados.nasa_photo.ui.notebook

/**
 * Группа интерфейсов для работ с ItemTouchHelper
 */
//Перемещение и удаление
interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

//Индиктатор пользователь схватил или отпустил элемент
interface ItemTouchHelperViewHolder {
    fun onItemSelect()
    fun onItemClear()
}