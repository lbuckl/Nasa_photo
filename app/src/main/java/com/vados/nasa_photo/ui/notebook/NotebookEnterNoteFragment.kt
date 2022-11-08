package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.weather.model.room.NoteItemEntity
import com.vados.nasa_photo.databinding.FragmentNotebookEnterNoteBinding
import com.vados.nasa_photo.ui.notebook.callbacks.AddItemCallback
import com.vados.nasa_photo.ui.notebook.callbacks.ReplaceItemCallback
import com.vados.nasa_photo.ui.notebook.callbacks.CancelCallback

/**
 * Фрагмент для добавления и редактирования заметок
 */
class NotebookEnterNoteFragment(
    private val noteContent: NoteItemEntity?,
    private val callbackAddItem: AddItemCallback,
    private val callbackReplaceItem: ReplaceItemCallback,
    private val callbackCancel: CancelCallback
    ):Fragment() {

    private var _binding: FragmentNotebookEnterNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotebookEnterNoteBinding.inflate(inflater)

        initContent()

        return binding.root
    }

    //Основная функция инициализации
    private fun initContent(){
        noteContent?.let { last ->
            binding.textInputNoteName.setText(last.header)
            binding.textInputNoteDescription.setText(last.description)
        }

        //Добавляется новый элемент или заменяется существующий
        binding.buttonApply.setOnClickListener{
            NoteItemEntity(
                0,
                binding.textInputNoteName.text.toString(),
                binding.textInputNoteDescription.text.toString(),
                "date"
            ).apply {
                if (noteContent == null) callbackAddItem.add(this)
                else{
                    this.id = noteContent.id
                    callbackReplaceItem.replace(this)
                }
            }

            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commitAllowingStateLoss()
        }

        binding.buttonCancel.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commitAllowingStateLoss()

            callbackCancel.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}