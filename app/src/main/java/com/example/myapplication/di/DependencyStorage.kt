package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.db.MyDatabase
import com.example.myapplication.repositories.CurrencyRepository
import com.example.myapplication.utils.Mappers

object DependencyStorage {

    fun init(applicationContext: Context) {
        Android.init(applicationContext)
        Database.init()
        Repository.init()
    }

    object Android {
        lateinit var applicationContext: Context
            private set

        fun init(applicationContext: Context) {
            Android.applicationContext = applicationContext
        }
    }

    object Network {
        private val loggingInterceptor = DependencyFactory.createLoggingInterceptor()
        private val httpClient = DependencyFactory.createHttpClient(loggingInterceptor)
        val retrofit = DependencyFactory.createRetrofit(httpClient)
    }

    object Api {
        val currencyApi = DependencyFactory.createApi(Network.retrofit)
    }

    object Database {
        lateinit var currencyDb: MyDatabase
            private set

        fun init() {
            currencyDb = DependencyFactory.createDatabase(Android.applicationContext)
        }
    }

    object Dao {
        val dao = DependencyFactory.createDao(Database.currencyDb)
    }

    object Repository {
        lateinit var repository: CurrencyRepository
            private set

        fun init() {
            repository = DependencyFactory.createRepository(Api.currencyApi, Dao.dao)
        }
    }

    object Mappers {
        val mappers = Mappers()
    }
}