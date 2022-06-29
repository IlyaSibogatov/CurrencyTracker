package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.BuildConfig
import com.example.myapplication.api.Api
import com.example.myapplication.db.MyDao
import com.example.myapplication.db.MyDatabase
import com.example.myapplication.repositories.CurrencyRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyFactory {

    fun createApi(retrofit: Retrofit) = retrofit.create(Api::class.java)

    fun createRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Api.BASE_URL)
        .client(client)
        .build()

    fun createHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(logger)
        }
        return httpClient.build()
    }

    fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    fun createDatabase(applicationContext: Context): MyDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java,
            "currencies"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun createRepository(
        api: Api,
        myDao: MyDao
    ) = CurrencyRepository(
        api,
        myDao
    )

    fun createDao(database: MyDatabase): MyDao {
        return database.dao()
    }
}