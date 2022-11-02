package com.vados.nasa_photo.viewmodel.notebook

import com.vados.nasa_photo.domain.NoteItem

sealed class NoteBookAppState {
    data class Success(val notes: ArrayList<NoteItem>): NoteBookAppState()
    data class Error(val error:String): NoteBookAppState()
    object Loading: NoteBookAppState()
}