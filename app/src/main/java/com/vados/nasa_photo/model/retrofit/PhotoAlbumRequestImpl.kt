package molchanov.hammertesttask.model.request

import com.google.gson.GsonBuilder
import com.vados.nasa_photo.model.retrofit.NasaRequestInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * класс для запроса фото дня из API NASA
 * основная функция для запроса: getRetrofitImpl()
 */
object PhotoAlbumRequestImpl {
    private const val baseUrl = "https://images-api.nasa.gov"
    private val podRetrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(createOkHttpClient(PODInterceptor()))
        .build().create(NasaRequestInterface::class.java)

    fun getRetrofitImpl(): NasaRequestInterface {
        return podRetrofit
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    //Перехватчик для отлавливания колбэков о результате загрузки
    class PODInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}