package molchanov.hammertesttask.model.request

import molchanov.hammertesttask.model.dto.MenuDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс запроса данных с API
 * @param q - основая тема звпроса
 * @param description - доп описание запроса
 * @param keywords - ключевые слова (если имеются)
 * @param media_type - тип медиафайла
 */
interface MenuRequestInterface {
    @GET("search")
    fun getMenu(
        @Query("q") q: String,
        @Query("description") description: String,
        @Query("keywords") keywords: String,
        @Query("media_type") media_type: String,
    ): Call<MenuDTO>
}