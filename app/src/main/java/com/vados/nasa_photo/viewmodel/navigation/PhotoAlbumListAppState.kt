package com.vados.nasa_photo.viewmodel.navigation

import com.gb.weather.domain.PhotoAlbumItem

/**
 * класс хранящий состояние ViewModel
 */
sealed class PhotoAlbumListAppState {
    data class Success(val menuListDTO: List<PhotoAlbumItem>): PhotoAlbumListAppState()
    data class Error(val error:Exception): PhotoAlbumListAppState()
    object Loading: PhotoAlbumListAppState()
}