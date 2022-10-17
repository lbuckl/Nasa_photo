package com.gb.weather.view.weatherlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gb.weather.domain.PhotoAlbumItem
import com.vados.nasa_photo.databinding.FragmentPhotoalbumMenuItemBinding

/**
 * Кастомный адаптер для вывода списка элементов меню в recyclerview
 */
class PhotoAlbumListRecyclerAdapter (private val weatherListCity:List<PhotoAlbumItem>):
    RecyclerView.Adapter<PhotoAlbumListRecyclerAdapter.PhotoViewHolder>() {

    //Создаёт ViewHolder объект опираясь на их количество, но с запасом, чтобы можно было скролить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = FragmentPhotoalbumMenuItemBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoViewHolder(binding.root)
    }

    //Связываем используемые текстовые метки с данными
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(weatherListCity[position])
    }

    //Возвращает количество элементов списка
    override fun getItemCount(): Int {
        return weatherListCity.size
    }

    //Класс который непосредственно отображает данные в каждом элементе recyclerview
    inner class PhotoViewHolder(view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(photoAlbumItem: PhotoAlbumItem){
            FragmentPhotoalbumMenuItemBinding.bind(itemView).apply {
                imageViewItemPicture.load(photoAlbumItem.link)
                textViewName.text = photoAlbumItem.name
                textViewDescription.text = photoAlbumItem.description
            }
        }
    }
}
