package molchanov.hammertesttask.model.dto

/**
 * Основной класс для запроса данных из API
 * @param collection соделржит массив данных для вывода в элементы меню
 *
 * Иерархия: Item -> Data (данные имя элемента и описание)
 *                -> Links (хранит ссылку на фото)
 */
data class MenuDTO(
    val collection: Collection
)