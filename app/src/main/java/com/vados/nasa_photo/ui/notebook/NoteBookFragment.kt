package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vados.nasa_photo.databinding.FragmentNotebookListBinding
import kotlinx.coroutines.cancel

class NoteBookFragment:Fragment() {
    private var _binding: FragmentNotebookListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotebookListBinding.inflate(inflater)
        //binding.notebookList.adapter =
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}