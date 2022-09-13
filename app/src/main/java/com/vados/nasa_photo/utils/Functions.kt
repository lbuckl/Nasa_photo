package com.vados.nasa_photo.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

fun View.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

//region функция для выдачи разрешения
/**
 * на вход подаются:
 * - имя разрешения permissionName например Manifest.permission.CALL_PHONE
 * - активити и контекст (можно передавать requireActivity(),requireContext() из фрагмента)
 * - уникальный код под которым будет зарегистрировано разрешение
 *
 */
fun getSinglePermission(permissionName: String,
                        activity: Activity,
                        context: Context,
                        requereCode:Int):Boolean {
    var textPermission = ""
    when (permissionName){
        (Manifest.permission.ACCESS_FINE_LOCATION) -> textPermission = "\"геолокация\""
        (Manifest.permission.READ_CONTACTS) -> textPermission = "\"чтение контактов\""
        (Manifest.permission.CALL_PHONE) -> textPermission = "\"вызовы\""
        (Manifest.permission.WRITE_EXTERNAL_STORAGE) -> textPermission = "\"вызовы\""
    }

    if (checkSinglePermission(permissionName,context)) {
        return true
        //Проверяем а не 2 ли это попытка запросить разрешение
    } else if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)){
        AlertDialog.Builder(context)
            .setTitle("Доступ к функции $textPermission")
            .setMessage("Для работы с функцией $textPermission нужно дать соответствующее разрешение")
            .setPositiveButton("Логично") { _, _ ->
                permissionRequest(
                    arrayOf(permissionName),activity,requereCode)
            }
            .setNegativeButton("Бред") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
        return checkSinglePermission(permissionName,context)
    } else {
        permissionRequest(arrayOf(permissionName),activity,requereCode)
        return checkSinglePermission(permissionName,context)
    }
}

//Функция, которая непосредственно отображает окно с подтверждением
private fun permissionRequest(permissions: Array<String>, activity: Activity, requestCode:Int) {
    ActivityCompat.requestPermissions(activity, permissions, requestCode)
}

/**
 * Функция для проверки "активно или отозвано разрешение (permission)"
 * ___________________________________________________________________________
 * на вход падаётся имя permissionName например Manifest.permission.CALL_PHONE
 * функция возвращает ответ о состоянии permission: true - активно, false - отозвано
 */
fun checkSinglePermission(permissionName: String, context: Context):Boolean{
    //val context = MyApp.getMyApp()
    val permission = ContextCompat.checkSelfPermission(context, permissionName)
    return permission == PackageManager.PERMISSION_GRANTED
}
//endregion