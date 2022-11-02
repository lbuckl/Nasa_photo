package com.vados.nasa_photo.utils

import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.gb.weather.model.room.NoteItemEntity
import com.google.android.material.snackbar.Snackbar
import com.vados.nasa_photo.R
import com.vados.nasa_photo.domain.NoteItem

//функция для отображения снэкбара c ошибкой и доп. сообщением
fun View.showSnackBarErrorMsg(
    errorMsg:String,
    text: String = "${rootView.resources.getString(R.string.error)}: $errorMsg",
    length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, text, length).show()
}

//функция для отображения информационного снэкбара
fun View.showSnackBarInfoMsg(
    infoMsg:String,
    text: String = "${rootView.resources.getString(R.string.info)}: $infoMsg",
    length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, text, length).show()
}

fun View.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

fun NotebookEntityToData(entityArray: ArrayList<NoteItemEntity>):MutableList<NoteItem>{
    return  entityArray.map { NoteItem(
        it.header,
        it.description,
        it.date
        )
    }.toMutableList()
}

fun NoteBookDataToEntity(item:NoteItem):NoteItemEntity{
    return NoteItemEntity(
        0,
        item.header,
        item.description,
        item.date
    )
}