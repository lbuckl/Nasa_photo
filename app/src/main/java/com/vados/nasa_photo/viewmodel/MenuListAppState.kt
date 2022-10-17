package molchanov.hammertesttask.viewmodel

import com.gb.weather.domain.MenuItem

/**
 * класс хранящий состояние ViewModel
 */
sealed class MenuListAppState {
    data class Succes(val menuListDTO: List<MenuItem>): MenuListAppState()
    data class Error(val error:Exception): MenuListAppState()
    object Loading: MenuListAppState()
}