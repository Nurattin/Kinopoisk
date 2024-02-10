package com.example.kinopoisk.di

import android.content.Context
import androidx.room.Room
import com.example.kinopoisk.BuildConfig
import com.example.kinopoisk.data.network_layer.KinopoiskApi
import com.example.kinopoisk.data.store_layer.FilmDatabase
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Named("Retrofit")
    @Singleton
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FilmDatabase::class.java,
        "kinopoisk_db"
    ).build()

    @Singleton
    @Provides
    fun provideFilmDao(db: FilmDatabase) = db.getFilmDao()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("Retrofit") gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): KinopoiskApi {
        return retrofit.create(KinopoiskApi::class.java)
    }

    class ApiKeyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("x-api-key", BuildConfig.API_KEY)
                .build()
            return chain.proceed(newRequest)
        }
    }
}
