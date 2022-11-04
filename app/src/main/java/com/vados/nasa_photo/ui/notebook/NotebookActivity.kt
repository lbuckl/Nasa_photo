package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.gb.weather.model.NotebookRepository
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.ActivityNotebookBinding
import com.vados.nasa_photo.utils.getAppTheme
import com.vados.nasa_photo.viewmodel.notebook.NoteBookAppState
import com.vados.nasa_photo.viewmodel.notebook.NoteBookViewModel

class NotebookActivity: AppCompatActivity() {
    private var _binding: ActivityNotebookBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: NotebookRecyclerAdapter

    companion object{
        lateinit var viewModel: NoteBookViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getAppTheme())
        super.onCreate(savedInstanceState)
        _binding = ActivityNotebookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onCreated()
    }

    private fun onCreated(){

        viewModel = ViewModelProvider(this)[NoteBookViewModel::class.java]
        viewModel.getLiveData().observeForever { t -> renderData(t) }

        binding.fab.setOnClickListener {
            val lastFragment = supportFragmentManager.findFragmentByTag("add_note")
            replaceFragment(lastFragment,NotebookEnterNoteFragment(callbackAdd),"add_note")
        }
    }

    private fun renderData(appState: NoteBookAppState){
        when(appState){
            is NoteBookAppState.Success ->{
                adapter = NotebookRecyclerAdapter(appState.notes,CBremoveItem)
                binding.notebookRecyclerNoteList.adapter = adapter
                ItemTouchHelper(CBitemTouchHelper(adapter)).attachToRecyclerView(binding.notebookRecyclerNoteList)
            }
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
        binding.fab.visibility = View.GONE
        binding.notebookRecyclerNoteList.visibility = View.GONE
    }

    /**
     * Коллбэк для добавления новой заметки
     */
    private val callbackAdd = CBaddItem {
        NotebookRepository.addItemToHistory(it)
        adapter.addItem(NotebookRepository.getHistoryList())
        binding.notebookRecyclerNoteList.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
    }

    /**
     * Коллбэк для удаления заметки заметки
     */
    private val CBremoveItem = CBremoveItem {
        NotebookRepository.deleteItemFromHistory(it)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}