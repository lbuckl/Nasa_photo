package com.vados.nasa_photo.ui.notebook

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vados.nasa_photo.databinding.FragmentEarthPhotoItemBinding
import com.vados.nasa_photo.databinding.FragmentNotebookItemBinding
import com.vados.nasa_photo.domain.NoteItem
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTOItem

class NotebookRecyclerAdapter(private val items:ArrayList<NoteItem>):
    RecyclerView.Adapter<NotebookRecyclerAdapter.NoteItemHolder>() {

    //Создаёт ViewHolder объект опираясь на их количество, но с запасом, чтобы можно было скролить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemHolder {
        val binding = FragmentNotebookItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteItemHolder(binding.root)
    }

    //Связываем используемые текстовые метки с данными
    override fun onBindViewHolder(holder: NoteItemHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
    //Класс который непосредственно отображает данные в каждом элементе recyclerview
    inner class NoteItemHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(noteItem: NoteItem){

        }
    }
}
