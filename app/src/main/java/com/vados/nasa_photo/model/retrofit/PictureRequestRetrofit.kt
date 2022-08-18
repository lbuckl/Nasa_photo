package com.vados.nasa_photo.model.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.vados.nasa_photo.model.PictureDTO
import com.vados.nasa_photo.utils.NASA_PICTURE_API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.UnknownHostException

object PictureRequestRetrofit {

    private val retrofitImpl = Retrofit.Builder()
    fun request():PictureDTO? {
        Log.d("@@@", "Request:request")
        try {
            retrofitImpl.baseUrl("https://api.nasa.gov/")
                .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            val api = retrofitImpl.build().create(PictureRequestInterface::class.java)
            return api.getPicture(NASA_PICTURE_API_KEY).execute().body()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.d("@@@", "IllegalStateException")
            return null
        } catch (e: RuntimeException) {
            e.printStackTrace()
            Log.d("@@@", "RuntimeException")
            return null
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Log.d("@@@", "UnknownHostException")
            return null
        }
    }
}