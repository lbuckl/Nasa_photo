package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.weather.model.room.NoteItemEntity
import com.vados.nasa_photo.databinding.FragmentNotebookEnterNoteBinding

/**
 * Фрагмент для добавления и редактирования заметок
 */
class NotebookEnterNoteFragment(
    private val noteContent: NoteItemEntity?,
    private val callbackAddItem: CBaddItem,
    private val callbackReplaceItem: CBreplaceItem,
    private val callbackCancel: CBcancel
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