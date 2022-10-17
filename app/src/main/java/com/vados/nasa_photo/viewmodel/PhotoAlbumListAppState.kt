package molchanov.hammertesttask.viewmodel

import com.gb.weather.domain.PhotoAlbumItem

/**
 * класс хранящий состояние ViewModel
 */
sealed class PhotoAlbumListAppState {
    data class Succes(val menuListDTO: List<PhotoAlbumItem>): PhotoAlbumListAppState()
    data class Error(val error:Exception): PhotoAlbumListAppState()
    object Loading: PhotoAlbumListAppState()
}