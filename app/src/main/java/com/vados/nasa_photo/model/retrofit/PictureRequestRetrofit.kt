package com.vados.nasa_photo.model.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.vados.nasa_photo.model.PictureDTO
import com.vados.nasa_photo.model.RequestInterface
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

object PictureRequestRetrofit: RequestInterface {
    private val retrofitImpl = Retrofit.Builder()
    override fun request():PictureDTO? {
        try {
            retrofitImpl.baseUrl("https://api.nasa.gov/")
                .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            val api = retrofitImpl.build().create(NasaPictureRequestInterface::class.java)
            return api.getPicture(NASA_PICTURE_API_KEY).execute().body()

        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.v("@@@", "IllegalStateException")
            return null
        } catch (e: RuntimeException) {
            e.printStackTrace()
            Log.v("@@@", "RuntimeException")
            return null
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Log.v("@@@", "UnknownHostException")
            return null
        }
    }
}