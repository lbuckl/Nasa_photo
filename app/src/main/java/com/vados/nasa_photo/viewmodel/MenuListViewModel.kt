package molchanov.hammertesttask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.weather.domain.MenuItem
import molchanov.hammertesttask.model.dto.MenuDTO
import molchanov.hammertesttask.model.request.MenuRequestImpl
import retrofit2.Call
import retrofit2.Response

class MenuListViewModel(private val liveData: MutableLiveData<MenuListAppState> = MutableLiveData<MenuListAppState>()) :
    ViewModel() {

    val getLiveData = {
        getMenuItems()
        liveData
    }

    fun getMenuItems() {
        liveData.postValue(MenuListAppState.Loading)

        MenuRequestImpl().getRetrofitImpl().getMenu(
            "earth",
            "earth",
            "russia",
            "image"
        ).enqueue(object : retrofit2.Callback<MenuDTO> {
            override fun onResponse(call: Call<MenuDTO>, response: Response<MenuDTO>) {
                val listResponce = MenuListAppState.Succes(menuDTOtoListMenuItem(response.body()!!))
                liveData.postValue(listResponce)
            }

            override fun onFailure(call: Call<MenuDTO>, t: Throwable) {
                liveData.postValue(MenuListAppState.Error(Exception("Loading Failure")))
            }
        })
    }

    private fun menuDTOtoListMenuItem(collection: MenuDTO): List<MenuItem> {
        val listMenu: MutableList<MenuItem> = mutableListOf()
        for (item in collection.collection.items) {
            listMenu.add(
                MenuItem(
                    item.data[0].center,
                    item.data[0].title,
                    item.links[0].href,
                    500.0
                )
            )
        }
        return listMenu
    }
}