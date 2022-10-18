package com.gb.weather.view.weatherlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gb.weather.domain.PhotoAlbumItem
import com.vados.nasa_photo.databinding.FragmentEarthPhotoItemBinding
import com.vados.nasa_photo.databinding.FragmentPhotoalbumItemBinding
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTOItem

/**
 * Кастомный адаптер для вывода списка элементов меню в recyclerview
 */
class EarthPhotoRecyclerAdapter (private val itemList:EarthPhotoDTO):
    RecyclerView.Adapter<EarthPhotoRecyclerAdapter.PhotoViewHolder>() {

    //Создаёт ViewHolder объект опираясь на их количество, но с запасом, чтобы можно было скролить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = FragmentEarthPhotoItemBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoViewHolder(binding.root)
    }

    //Связываем используемые текстовые метки с данными
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    //Возвращает количество элементов списка
    override fun getItemCount(): Int {
        return itemList.size
    }

    //Класс который непосредственно отображает данные в каждом элементе recyclerview
    inner class PhotoViewHolder(view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(earthPhotoItem: EarthPhotoDTOItem){
            FragmentEarthPhotoItemBinding.bind(itemView).apply {
                imageViewItemEarth.load(earthPhotoItem.image)
                textViewNameEarth.text = earthPhotoItem.date
            }
        }
    }
}
