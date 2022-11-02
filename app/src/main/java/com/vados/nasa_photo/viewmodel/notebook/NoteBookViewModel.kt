package com.vados.nasa_photo.viewmodel.notebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel реализующая бизнес-логику работы фрагмента для отображения
 * фотографии получаемой с сайта NASA
 */
class NoteBookViewModel(private val liveData: MutableLiveData<NoteBookAppState> = MutableLiveData<NoteBookAppState>()
): ViewModel(){

    val getLiveData = {
        liveData
    }
}