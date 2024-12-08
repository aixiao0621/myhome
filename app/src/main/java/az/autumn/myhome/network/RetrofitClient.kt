package az.autumn.myhome.network

import android.content.Context
import az.autumn.myhome.data.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object RetrofitClient {
//    private const val BASE_URL = "http://192.168.0.239:8989" // Replace with your backend URL
//
//    val apiService: ApiService = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ApiService::class.java)
//}

class RetrofitClient(context: Context) {

    private val repository = SettingsRepository(context)

    // 同步阻塞方式获取当前的后端地址（不推荐在主线程调用）
    // 更好的方式是将此过程放入协程或者通过依赖注入在应用启动时获取并传入
    private val baseUrl: String = runBlocking {
        repository.backendUrlFlow.first()
    }

    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}