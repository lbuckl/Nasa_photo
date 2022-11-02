package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vados.nasa_photo.databinding.FragmentNotebookListBinding
import com.vados.nasa_photo.viewmodel.notebook.NoteBookAppState
import com.vados.nasa_photo.viewmodel.notebook.NoteBookViewModel
import kotlinx.coroutines.cancel

class NotebookFragment():Fragment() {
    private var _binding: FragmentNotebookListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotebookListBinding.inflate(inflater)
        NotebookActivity.viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        return binding.root
    }

    private fun renderData(appState: NoteBookAppState){
        when(appState){
            is NoteBookAppState.Success ->{
                binding.notebookRecyclerNoteList.adapter = NotebookRecyclerAdapter(appState.notes)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}