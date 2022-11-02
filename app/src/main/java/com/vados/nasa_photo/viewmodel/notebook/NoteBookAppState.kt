package com.vados.nasa_photo.viewmodel.notebook

import com.gb.weather.model.room.NoteItemEntity

sealed class NoteBookAppState {
    data class Success(val notes: MutableList<NoteItemEntity>): NoteBookAppState()
    data class Error(val error:String): NoteBookAppState()
    object Loading: NoteBookAppState()
}