package com.example.myapplication.di

import org.koin.dsl.module

val apiModule = module {
    single { DependencyFactory.createLoggingInterceptor() }
    single { DependencyFactory.createHttpClient(get()) }
    single { DependencyFactory.createRetrofit(get()) }
    single { DependencyFactory.createApi(get()) }
    single { DependencyFactory.createDatabase(get()) }
    single { DependencyFactory.createRepository(get(), get()) }
    single { DependencyFactory.createDao(get()) }
    single { DependencyStorage.Mappers }
}

val appModules = listOf(apiModule)