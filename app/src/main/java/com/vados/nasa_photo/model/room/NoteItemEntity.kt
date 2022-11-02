package com.gb.weather.model.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NoteItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val header: String,
    val description:String,
    val date: String
):Parcelable