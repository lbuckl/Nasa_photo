package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
            val lastFragment = supportFragmentManager.findFragmentByTag("add_note")
            replaceFragment(lastFragment,NotebookEnterNoteFragment(callbackAdd),"add_note")
        }
    }

    /**
     * Функция для замены фрагмента в контейнере
     * если фрагмент ещё живой, то возвращает его в контейнер
     * если фрагмента нет, то создаёт новый
     */
    fun replaceFragment(findFragment: Fragment?, newFragment: Fragment, flag:String){
        supportFragmentManager.beginTransaction().apply {
            if (findFragment == null) {
                add(R.id.notebook_container, newFragment, flag)
                    .addToBackStack("Contacts")
                    .commitAllowingStateLoss()
            } else {
                replace(R.id.notebook_container, findFragment, flag)
                    .commitAllowingStateLoss()
            }
        }
    }

    private val callbackAdd = AddItemCB {
        NotebookRepository.addItemToHistory(it)
        NotebookRepository.getHistoryList()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}