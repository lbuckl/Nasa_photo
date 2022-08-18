package com.vados.nasa_photo.model

import com.google.gson.annotations.SerializedName

class PictureDTO (
        @field:SerializedName("copyright") val copyright: String?,
        @field:SerializedName("date") val date: String?,
        @field:SerializedName("explanation") val explanation: String?,
        @field:SerializedName("media_type") val mediaType: String?,
        @field:SerializedName("title") val title: String?,
        @field:SerializedName("url") val url: String?,
        @field:SerializedName("hdurl") val hdurl: String?
)