package com.vados.nasa_photo.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.time.LocalDate
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Объект реализующий функции для сохранения фотографий в память смартфона
 */
object ImageToMemoryLoader {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    //функция сохраняет картинку формата drawable в память смартфона
    suspend fun savePicture(context: Context, draw: Drawable) = suspendCoroutine<String>{
        //переменные для создания пути сохранения файла в память сматрфона
        val date = LocalDate.now()
        val fileName = "NasaPicture_$date" //имя файла
        val folderToSave = context.filesDir.toString()//директория для сохранения
        val file = File(folderToSave,fileName)

        if (file.isFile){
            it.resume("Файл уже загружен")
            file.delete()
        }
        else{
            thread{
                val fOut: OutputStream?
                try {
                    //Открывает поток
                    fOut = FileOutputStream(file)

                    //преобразуем в битмап и сохраняем в формате jpeg с 50% сжатием
                    draw.toBitmap().compress(Bitmap.CompressFormat.JPEG,50,fOut)

                    //закрываем поток
                    fOut.flush()
                    fOut.close()

                    // регистрация в фотоальбоме
                    MediaStore.Images.Media.insertImage(context.contentResolver,
                        file.absolutePath,file.name,file.name)
                    if (file.isFile) it.resume("Файл удачно загружен")
                }catch (e: IOException){
                    it.resume("Файл не загружен!!!")
                    e.printStackTrace()
                }catch (e:NullPointerException){
                    it.resume("Файл не загружен!!!")
                    e.printStackTrace()
                }
            }
        }
    }
}