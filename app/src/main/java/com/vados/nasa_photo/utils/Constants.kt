package com.vados.nasa_photo.utils

import com.vados.nasa_photo.BuildConfig

/**
 * Константа с ключем API от NASA
 * API ключ можно сгенерировать по ссылке https://api.nasa.gov/
 * можно просто заменить строку BuildConfig.NASA_PICTURE_API_KEY
 * либо создать файл apikey.properties и вписать ключ
 * nasa_api_key = <Ваш ключ> (В СКОБКАХ "")
 */
const val NASA_PICTURE_API_KEY = BuildConfig.NASA_PICTURE_API_KEY

/**
 * Группа констант для фрагмента "Настройки"
 */
const val PREF_SETTINGS = "PREF_SETTINGS"
//Настройки темы приложения
const val PREF_THEME_INT = "PREF_THEME_INT"
const val THEME_LIGHT = 0
const val THEME_DARK = 1
const val THEME_RED = 2
const val THEME_SPACE = 3

//Константа для хранения флага первого запуска приложения
const val INITIALIZATION = "init"
const val FIRST_ACTIVE = "first_active"
