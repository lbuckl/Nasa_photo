package com.vados.nasa_photo.ui.notebook.callbacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vados.nasa_photo.ui.notebook.ItemTouchHelperAdapter
import com.vados.nasa_photo.ui.notebook.NotebookRecyclerAdapter

/**
 * Класс для организации перемещения и свайпа элементов recyclerView
 * работает как коллбэк передавая в адаптер данные:
 * - о перемещении (вверх/вниз) fun onMove
 * - о смещении/свайпе (влево/вправо) fun onSwiped
 */
class ItemTouchHelperCallback(private val callback: ItemTouchHelperAdapter): ItemTouchHelper.Callback() {
    //Функция задаёт варианты взаимодействия с элементами recyclerView
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipe = ItemTouchHelper.START
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(drag,swipe)
    }
    //Функция организующая коллбэк по перемещению элемента recyclerView вверх/вниз
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        callback.onItemMove(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }
    //Функция организующая коллбэк по смещению элемента recyclerView влево/вправо
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        callback.onItemDismiss(viewHolder.adapterPosition)
    }

    //Функция индикатор "эпользователь схватил элемент recyclerView"
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            (it as NotebookRecyclerAdapter.NoteItemHolder).onItemSelect()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }
    //Функция индикатор "эпользователь отпустил элемент recyclerView"
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        (viewHolder as NotebookRecyclerAdapter.NoteItemHolder).onItemClear()
    }
}