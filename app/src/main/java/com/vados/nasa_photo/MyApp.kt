package com.vados.nasa_photo

import android.app.Application
import androidx.room.Room
import com.gb.weather.model.room.NoteBookHistoryDatabase
import com.vados.nasa_photo.utils.NOTEBOOK_ROOM_DB_NAME

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        private var noteBookHistoryDatabase: NoteBookHistoryDatabase? = null

        fun getMyApp() = myApp!!
        fun getNotesFromDatabase(): NoteBookHistoryDatabase {
            if (noteBookHistoryDatabase == null) {
                noteBookHistoryDatabase = Room.databaseBuilder(
                    getMyApp(),
                    NoteBookHistoryDatabase::class.java,
                    NOTEBOOK_ROOM_DB_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return noteBookHistoryDatabase!!
        }
    }


}