package com.vados.nasa_photo.ui.notebook

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vados.nasa_photo.databinding.ActivityNotebookBinding

class NoteBookActivity: AppCompatActivity() {
    private var _binding: ActivityNotebookBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("@@@","Notebook")
        _binding = ActivityNotebookBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}