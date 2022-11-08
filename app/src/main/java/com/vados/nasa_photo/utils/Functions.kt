package com.vados.nasa_photo.utils

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.Toast
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
    MyApp.getMyApp().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE).let {
        return when (it.getInt(PREF_THEME_INT, THEME_SPACE)){
            0 -> R.style.Theme_Nasa_photo
            1 -> R.style.Theme_Dark
            2 -> R.style.Theme_Red
            3 -> R.style.Theme_Space
            else -> R.style.Theme_Space
        }
    }
}

//region
/**
 * Функция ищет в [text] все слова списка[searchWords] и раскрашивает их в цвет [color]
 * @param color - цвет в формате int
 * @param spanFlag - флаг библиотеки Span (например Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
 * вовращает SpannableString
 */
fun setSpanColorByWord(text: String, searchWords:List<String>, color: Int, spanFlag:Int):SpannableString =
    SpannableString(text).apply {
        searchWords.forEach { word ->
            text.indexesOf(word,true).forEach {
                this.setSpan(
                    ForegroundColorSpan(
                        color),
                    it,it + word.length,
                    spanFlag
                )
            }
        }
    }

//Фунция ищет слово в тексте и возвращает массив первых индексов
fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
    (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
        .findAll(this).map { it.range.first }.toList()

