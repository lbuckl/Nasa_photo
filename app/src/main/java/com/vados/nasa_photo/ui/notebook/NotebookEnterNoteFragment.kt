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
class NotebookEnterNoteFragment(val callbackCBaddItem: CBaddItem):Fragment() {
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
        binding.buttonApply.setOnClickListener{
            NoteItemEntity(
                0,
                binding.textInputNoteName.text.toString(),
                binding.textInputNoteDescription.text.toString(),
                "date"
            ).apply {
                callbackCBaddItem.add(this)
            }

            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commitAllowingStateLoss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}