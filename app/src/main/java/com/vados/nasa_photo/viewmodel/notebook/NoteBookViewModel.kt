package com.vados.nasa_photo.viewmodel.notebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.weather.model.NotebookRepository
import java.io.IOException

/**
 * ViewModel реализующая бизнес-логику работы фрагмента для отображения
 * фотографии получаемой с сайта NASA
 */
class NoteBookViewModel(private val liveData: MutableLiveData<NoteBookAppState> = MutableLiveData<NoteBookAppState>()
): ViewModel(){

    val getLiveData = {
        getNotebookList()
        liveData
    }

    private fun getNotebookList(){
        try {
            val listItems = NotebookRepository.getHistoryList()
            liveData.postValue(NoteBookAppState.Success(listItems))
        }catch (e:IOException){
            e.printStackTrace()
            liveData.value = NoteBookAppState.Error("Ошибка запроса из БД")
        }
    }
}