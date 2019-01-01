import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application() {
    private val baseURL = "http://bghgu.tk:8080/"
    lateinit var networkService: NetworkService

    companion object {
        lateinit var instance: ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetWork()
    }

    fun buildNetWork() {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        networkService = retrofit.create(NetworkService::class.java)
    }
}
