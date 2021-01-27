package ua.oleksii.fitplantest.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.oleksii.fitplantest.managers.ConnectionManager
import ua.oleksii.fitplantest.managers.SharedPreferencesManager
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.model.network.AuthInterceptor
import ua.oleksii.fitplantest.model.network.NetworkUrls
import ua.oleksii.fitplantest.model.network.cache.OfflineCacheInterceptor
import ua.oleksii.fitplantest.model.network.cache.OnlineCacheInterceptor
import ua.oleksii.fitplantest.utils.DataStoreManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    companion object {
        private const val GENERAL_TIMEOUT = 25L
        private const val CONNECT_TIMEOUT = GENERAL_TIMEOUT
        private const val WRITE_TIMEOUT = GENERAL_TIMEOUT
        private const val READ_TIMEOUT = GENERAL_TIMEOUT
        private const val CACHE_SIZE = 20L * 1024 * 1024
    }

    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE)

    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        cache: Cache,
        authInterceptor: AuthInterceptor,
        onlineCacheInterceptor: OnlineCacheInterceptor,
        offlineCacheInterceptor: OfflineCacheInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(authInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .addNetworkInterceptor(onlineCacheInterceptor)
            .addNetworkInterceptor(ChuckInterceptor(context))
            .cache(cache)
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NetworkUrls.MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideApiRequestService(retrofit: Retrofit): ApiRequestService = retrofit.create(ApiRequestService::class.java)

    @Provides
    fun getDataStoreManager(@ApplicationContext context:Context):DataStoreManager = DataStoreManager(context)

}