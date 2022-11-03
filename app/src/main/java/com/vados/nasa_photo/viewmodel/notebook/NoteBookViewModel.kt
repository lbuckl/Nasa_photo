package com.vados.nasa_photo.viewmodel.notebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.weather.model.NotebookRepository
import com.gb.weather.model.room.NoteItemEntity

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

    private fun getActualAppState(){
        when (liveData.value){
            is NoteBookAppState.Success -> {

            }
        }
    }

    private fun getNotebookList(){
        liveData.postValue(NoteBookAppState.Success(NotebookRepository.getHistoryList()))
    }

    fun addNoteItemToDB(item:NoteItemEntity){
        NotebookRepository.addItemToHistory(item)
        getNotebookList()
    }

    fun replaceNoteItemToDB(item:NoteItemEntity){
        NotebookRepository.replaceItemInHistory(item)
        getNotebookList()
    }

    fun deleteNoteItemFromDB(item:NoteItemEntity){
        NotebookRepository.deleteItemFromHistory(item)
        getNotebookList()
    }
}