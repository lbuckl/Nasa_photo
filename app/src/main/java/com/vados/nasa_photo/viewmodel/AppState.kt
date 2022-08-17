package com.vados.nasa_photo.viewmodel

import java.lang.Exception

sealed class AppState {
    data class Succes(val pictureName:String): AppState()
    data class Error(val error:Exception): AppState()
    object Loading: AppState()
}