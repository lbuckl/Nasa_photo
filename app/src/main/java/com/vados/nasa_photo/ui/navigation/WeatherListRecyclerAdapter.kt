package com.gb.weather.view.weatherlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gb.weather.domain.MenuItem
import com.vados.nasa_photo.databinding.FragmentMarketMenuItemBinding

/**
 * Кастомный адаптер для вывода списка элементов меню в recyclerview
 */
class WeatherListRecyclerAdapter (private val weatherListCity:List<MenuItem>):
    RecyclerView.Adapter<WeatherListRecyclerAdapter.WeatherViewHolder>() {

    //Создаёт ViewHolder объект опираясь на их количество, но с запасом, чтобы можно было скролить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = FragmentMarketMenuItemBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherViewHolder(binding.root)
    }

    //Связываем используемые текстовые метки с данными
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherListCity[position])
    }

    //Возвращает количество элементов списка
    override fun getItemCount(): Int {
        return weatherListCity.size
    }

    //Класс который непосредственно отображает данные в каждом элементе recyclerview
    inner class WeatherViewHolder(view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(menuItem: MenuItem){
            FragmentMarketMenuItemBinding.bind(itemView).apply {
                /**
                 * ВНИМАНИЕ!!! КОСТЫЛЬ!!!
                 * Используемое API присылает картинки разных пропорций и размеров,
                 * поэтому изображение заменено на рандомное из интернета
                 * можно раскомментировать строку ниже и проверить работоспособность
                 */
                imageViewMenuItemPicture.load(menuItem.link)
                textViewName.text = menuItem.name
                textViewDescription.text = menuItem.description
            }
        }
    }
}
