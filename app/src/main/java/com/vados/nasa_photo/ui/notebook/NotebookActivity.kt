package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gb.weather.model.NotebookRepository
import com.gb.weather.model.room.NoteItemEntity
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.ActivityNotebookBinding
import com.vados.nasa_photo.viewmodel.notebook.NoteBookViewModel

class NotebookActivity: AppCompatActivity() {
    private var _binding: ActivityNotebookBinding? = null
    private val binding get() = _binding!!

    companion object{
        lateinit var viewModel: NoteBookViewModel
    }

    val notebookFragment = NotebookFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("@@@","Notebook")
        _binding = ActivityNotebookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onCreated()
    }

    private fun onCreated(){

        viewModel = ViewModelProvider(this)[NoteBookViewModel::class.java]

        supportFragmentManager.beginTransaction()
            .add(R.id.notebook_container,NotebookFragment())
            .addToBackStack("First")
            .commitAllowingStateLoss()

        binding.fab.setOnClickListener {
            val a = NotebookRepository.getHistoryList()
            viewModel.deleteNoteItemFromDB(NoteItemEntity(
                a[1].id,"Header","Description_new","date-date"
            ))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}