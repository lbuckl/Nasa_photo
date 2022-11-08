package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.gb.weather.model.NotebookRepository
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.ActivityNotebookBinding
import com.vados.nasa_photo.ui.notebook.callbacks.AddItemCallback
import com.vados.nasa_photo.ui.notebook.callbacks.ItemTouchHelperCallback
import com.vados.nasa_photo.ui.notebook.callbacks.ReplaceItemCallback
import com.vados.nasa_photo.ui.notebook.callbacks.CancelCallback
import com.vados.nasa_photo.utils.getAppTheme
import com.vados.nasa_photo.utils.toast
import com.vados.nasa_photo.viewmodel.notebook.NoteBookAppState
import com.vados.nasa_photo.viewmodel.notebook.NoteBookViewModel

/**
 * Активити "Записная книжка"
 */
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

    /**
     * Основная функция инициализации
     */
    private fun onCreated(){
        viewModel = ViewModelProvider(this)[NoteBookViewModel::class.java]
        viewModel.getLiveData().observeForever { t -> renderData(t) }

        binding.fab.setOnClickListener {
            addNoteFragment()
        }
    }

    /**
     * Функция обработки данных состояний Вью модели
     */
    private fun renderData(appState: NoteBookAppState){
        when(appState){
            is NoteBookAppState.Success -> {
                adapter = NotebookRecyclerAdapter(appState.notes,callbackReplaceAdapter)
                binding.notebookRecyclerNoteList.adapter = adapter
                ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.notebookRecyclerNoteList)
            }
            is NoteBookAppState.Error -> {
                binding.notebookRecyclerNoteList.toast(appState.error)
            }
        }
    }

    /**
     * Функция для замены фрагмента добавления/изменения заметки
     * если фрагмент ещё живой, то возвращает его в контейнер
     * если фрагмента нет, то создаёт новый
     */
    private fun addNoteFragment(){
        supportFragmentManager.let {
            val last = it.findFragmentByTag("add_note")
            if (last == null){
                it.beginTransaction()
                    .replace(R.id.notebook_container, NotebookEnterNoteFragment(
                        null,
                        callbackAdd,
                        callbackReplace,
                        callbackCancel
                    ), "add_note").commit()
            }
            else{
                it.beginTransaction()
                    .replace(R.id.notebook_container, last)
                    .commit()
            }
        }

        binding.fab.visibility = View.GONE
        binding.notebookRecyclerNoteList.visibility = View.GONE
    }

    /**
     * Коллбэк для добавления новой заметки
     */
    private val callbackAdd = AddItemCallback {
        NotebookRepository.addItemToHistory(it)
        adapter.addItem(NotebookRepository.getHistoryList())
        binding.notebookRecyclerNoteList.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
    }

    /**
     * Коллбэк для изменения заметки от адаптера
     */
    private val callbackReplaceAdapter = ReplaceItemCallback {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.notebook_container, NotebookEnterNoteFragment(
                    it,
                    callbackAdd,
                    callbackReplace,
                    callbackCancel
                ), "replace_note").commit()

        binding.fab.visibility = View.GONE
        binding.notebookRecyclerNoteList.visibility = View.GONE
    }

    /**
     * Коллбэк для изменения заметки от фрагмента
     */
    private val callbackReplace = ReplaceItemCallback{
        NotebookRepository.replaceItemInHistory(it)
        adapter.replaceItem(NotebookRepository.getHistoryList())
        binding.notebookRecyclerNoteList.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
    }

    /**
     * Коллбэк для отмены действий по добавлению или изменению заметок
     */
    private val callbackCancel = object : CancelCallback {
        override fun cancel() {
            binding.notebookRecyclerNoteList.visibility = View.VISIBLE
            binding.fab.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}