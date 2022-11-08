package com.vados.nasa_photo.utils

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.text.set
import com.gb.weather.model.room.NoteItemEntity
import com.google.android.material.snackbar.Snackbar
import com.vados.nasa_photo.MyApp
import com.vados.nasa_photo.R

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

//функция для отображения toast
fun View.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

//Фунция возвращающая сохранённую в настрйоках тему
fun getAppTheme():Int{
    val sharedPref = MyApp.getMyApp().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
    return when (sharedPref.getInt(PREF_THEME_INT, THEME_SPACE)){
        0 -> R.style.Theme_Nasa_photo
        1 -> R.style.Theme_Dark
        2 -> R.style.Theme_Red
        3 -> R.style.Theme_Space
        else -> R.style.Theme_Space
    }
}

/**
 * Функция ищет в [text] все слова [searchWord] и раскрашивает их в цвет [color]
 * вовращает SpannableString
 */
fun setSpanColorByWord(text: String, searchWord:String, color: Int):SpannableString =
    SpannableString(text).apply {
        text.indexesOf(searchWord,true).forEach {
            this.setSpan(
                ForegroundColorSpan(
                    color),
                it,it + searchWord.length,
                33
            )
        }
    }

fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
    (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
        .findAll(this).map { it.range.first }.toList()

